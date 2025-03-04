package com.shop.service;

import com.shop.entity.Product;
import com.shop.mapper.ProductMapper;
import com.shop.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.shop.entity.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public List<Product> findAll() {
        return productRepository.findAll();
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
        return productRepository.existsByCategoryId(categoryId) != null;
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