package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shop.entity.Category;
import com.shop.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    Product findByNameAndCategory(String name, Category category);

    Product findByPathAndCategory(String path, Category category);

    List<Product> findAllByCategory(Category category);


}