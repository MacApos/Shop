package pl.coderslab.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.repository.ProductRepository;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.impl.CategoryServiceImpl;
import pl.coderslab.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductServiceImpl(CategoryService categoryService, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    private final List<Product> productList = new ArrayList<>();

    @Override
    public List<Product> findAllByCategory(Category category) {
        List<Category> children = categoryRepository.findAllByParentCategory(category);
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

    public List<Category> getAllCategories(Category category){
        List<Category> parents = new ArrayList<>();
        if(category!=null){
           parents.addAll( getAllCategories(category.getParentCategory()));
        }
        return parents;
    }

    @Override
    public Product findByCategory(Category category) {
        getAllCategories(category);
        return null;
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
        if (categoryId == null || categoryRepository.findById(categoryId).isEmpty()) {
            throw new Error("Category doesn't exists.");
        }
        List<Category> children = categoryRepository.findAllByParentCategory(category);
        if (!children.isEmpty()) {
            throw new Error("Can't add product to parent category.");
        }

        product.setPath(categoryService.normalizeName(name));
        productRepository.save(product);
    }
}