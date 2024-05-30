package pl.coderslab.service;

import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllByCategory(Category category);

    List<Product> findAll();

    Product findById(Long id);

    Product findByPathAndCategory(String path, Category category);

    void save(Product product);

}