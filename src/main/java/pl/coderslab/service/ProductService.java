package pl.coderslab.service;

import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllByCategory(Category category);

    List<Product> findAll();

    void save(Product product);

}