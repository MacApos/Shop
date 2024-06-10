package pl.coderslab.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
   private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void givenInvalidId_whenFindById_thenReturnNull(){
        Product product = productRepository.findById(-1L).orElse(null);
        assertThat(product).isNull();
    }

//    @Test
//    void givenCategory_whenFindAllByCategory_thenReturnAllChildCategories(){
//        Category parent = new Category("parent", null);
//        Category child1 = new Category("child1", parent);
//        Category child2 = new Category("child2", parent);
//
//        entityManager.persist(parent);
//        entityManager.persist(child1);
//        entityManager.persist(child2);
//
//        List<Product> children = productRepository.findAllByCategory(parent);
//
//        assertThat(children).hasSize(3).extracting(Category::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
//
//    }

}