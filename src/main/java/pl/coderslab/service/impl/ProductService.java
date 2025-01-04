package pl.coderslab.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> recursiveFindAllByCategory(Category category) {
        List<Category> children = categoryService.findAllByParentCategory(category);
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
            List<Category> children = categoryRepository.findAllChildrenByParent(cat);
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


    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    public Product findByPathAndCategory(String path, Category category) {
        return productRepository.findByPathAndCategory(path, category);
    }


    public void save(Product product) {
        Category category = product.getCategory();
        if (category == null) {
            throw new Error("Category doesn't exists.");
        }
        String name = product.getName();
        if (productRepository.findByNameAndCategory(name, category) != null) {
            throw new Error("Product already exists.");
        }
        Long categoryId = category.getId();
        if (categoryId == null || categoryService.findById(categoryId) == null) {
            throw new Error("Category doesn't exists.");
        }
        List<Category> children = categoryService.findAllByParentCategory(category);
        if (!children.isEmpty()) {
            throw new Error("Can't add product to parent category.");
        }

        product.setPath(categoryService.normalizeName(name));
        productRepository.save(product);
    }
}