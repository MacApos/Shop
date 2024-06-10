package pl.coderslab.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.repository.ProductRepository;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public ProductServiceImpl(CategoryService categoryService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllByCategory(Category category) {
        List<Category> children = categoryService.findAllByParentCategory(category);
        List<Product> productList = new ArrayList<>();

        if (!children.isEmpty()) {
            for (Category child : children) {
                productList.addAll(findAllByCategory(child));
            }
        } else {
            return productRepository.findAllByCategory(category);
        }
        return productList;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product findByPathAndCategory(String path, Category category) {
        return productRepository.findByPathAndCategory(path, category);
    }

    @Override
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
        if (categoryId == null || categoryService.findById(categoryId)==null) {
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