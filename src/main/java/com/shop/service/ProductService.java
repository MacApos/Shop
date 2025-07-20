package com.shop.service;

import com.shop.model.Product;
import com.shop.mapper.ProductMapper;
import com.shop.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.shop.model.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.data.domain.Sort.Direction.fromString;

@Service
@RequiredArgsConstructor
public class ProductService extends AbstractService<Product> {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final EntityManager entityManager;

    public List<Product> recursiveFindAllByCategory(Category category) {
        List<Category> children = categoryService.findAllByParent(category);
        List<Product> productList = new ArrayList<>();

        if (!children.isEmpty()) {
            for (Category child : children) {
                productList.addAll(recursiveFindAllByCategory(child));
            }
        } else {
            return productRepository.findAllByCategory(category);
        }
        return productList;
    }

    public List<Product> findAllByCategory(Category category) {
        ArrayList<Category> categories = new ArrayList<>(List.of(category));
        ArrayList<Product> products = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            List<Category> children = categoryService.findAllByParent(cat);
            if (children.isEmpty()) {
                products.addAll(productRepository.findAllByCategory(cat));
            } else {
                categories.addAll(children);
            }
        }
        return products;
    }

    public List<Product> findAll(Optional<Integer> categoryId, Optional<Integer> pageNumber, Optional<Integer> pageSize,
                                 Optional<String> sortProperties, Optional<String> sortDirection) {
        Pageable pageable = Pageable.unpaged();
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            Sort.Direction direction = fromString(sortDirection
                    .filter(d -> d.equalsIgnoreCase("desc"))
                    .orElse("asc"));
            LinkedHashSet<String> properties = sortProperties
                    .map(p -> new LinkedHashSet<>(Arrays.asList(p.split(","))))
                    .orElseGet(LinkedHashSet::new);
            properties.add("name");
            Sort sort = Sort.by(direction, properties.toArray(new String[0]));

            int count = categoryId
                    .map(productRepository::countAllByCategory)
                    .orElseGet(productRepository::count).intValue();
            // 1 >= pageSize <= count
            int size = Math.min(Math.max(1, pageSize.get()), count);
            int maxPage = (int) (Math.ceil((double) count / size));
            // 1 >= pageNumber <= maxPage
            int chosenPage = Math.min(Math.max(1, pageNumber.get()), maxPage) - 1;
            pageable = PageRequest.of(chosenPage, size, sort);
        }

        Pageable finalPageable = pageable;
        return categoryId
                .map(id -> productRepository.findAllByCategory(finalPageable, id))
                .orElseGet(() -> productRepository.findAll(finalPageable))
                .getContent();
    }

    public boolean existsByNameAndCategory(String name, Category category) {
        return productRepository.existsByNameAndCategory(name, category);
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product findByPathAndCategory(String path, Category category) {
        return productRepository.findByPathAndCategory(path, category);
    }

    public boolean existByCategoryId(Long categoryId) {
        return productRepository.existsByCategoryId(categoryId) == 1;
    }

    @Transactional
    public void save(Product product) {
        entityManager.persist(product);
        product.setPath(categoryService.normalizeName(product.getName()) + "-" + product.getId());
    }

    @Transactional
    public void update(Product product) {
        Product existingProduct = findById(product.getId());
        productMapper.update(product, existingProduct);
        save(existingProduct);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}