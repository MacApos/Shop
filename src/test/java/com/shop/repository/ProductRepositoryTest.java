package com.shop.repository;

import com.shop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.shop.entity.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
//@Import(RepositoryConfiguration.class)
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Category parent;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void initProducts() {
        parent = new Category("Parent", null);
        product1 = new Product("Product1", "Description1", 1.0, "/image1", "product1", parent);
        product2 = new Product("Product2", "Description2", 1.0, "/image2", "product2", parent);
        entityManager.persist(parent);
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();
    }

    @Test
    public void givenCategory_whenFindAllByCategory_thenReturnAllCategoryProducts() {
        List<Product> products = productRepository.findAllByCategory(parent);
        assertThat(products).hasSize(2).extracting(Product::getName)
                .containsOnly(product1.getName(), product2.getName());
    }

    @Test
    public void givenNameAndCategory_whenFindByPathAndCategory_thenReturnProduct() {
        String name = product1.getName();
        Product product = productRepository.findByNameAndCategory(name, parent);
        assertThat(product).extracting("name", "category").containsOnly(name, parent);
    }

    @Test
    public void givenPathAndCategory_whenFindByPathAndCategory_thenReturnProduct() {
        String normalizedName = product1.getPath();
        Product product = productRepository.findByPathAndCategory(normalizedName, parent);
        assertThat(product).extracting("path", "category").containsOnly(normalizedName, parent);
    }

}