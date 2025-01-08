package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    Product findByNameAndCategory(String name, Category category);

    Product findByPathAndCategory(String path, Category category);

    List<Product> findAllByCategory(Category category);


}