//package pl.coderslab.service;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import pl.coderslab.entity.Category;
//import pl.coderslab.entity.Product;
//import pl.coderslab.repository.CategoryRepository;
//import pl.coderslab.repository.ProductRepository;
//import pl.coderslab.service.impl.CategoryServiceImpl;
//import pl.coderslab.service.impl.ProductServiceImpl;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class ProductServiceImplIntegrationTest {
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
////    @TestConfiguration
////    class P {
////        @Bean
////        public CategoryService categoryService() {
////            return new CategoryServiceImpl(categoryRepository);
////        }
////
////        @Bean
////        public ProductService productService() {
////            return new ProductServiceImpl(categoryService, productRepository);
////        }
////
////    }
//
//    @Autowired
//    private CategoryServiceImpl categoryService;
//
//    @Autowired
//    private ProductServiceImpl productService;
//
//    private Category category;
//    private Product product;
//
//    @BeforeEach
//    void initProduct() {
//        categoryService = new CategoryServiceImpl(categoryRepository);
//        productService = new ProductServiceImpl(categoryService, productRepository);
//        category = new Category("Category", null);
//        category.setId(1L);
//        product = new Product("Product", "Description1", 1.0, "/image1", category);
//    }
//
//    @Test
//    void givenNotExistingCategory_whenSaveProduct_thenThrowError() {
//        assertThrows(Error.class, () -> productService.save(product));
//    }
//
//    @Test
//    void givenNullCategory_whenSaveProduct_thenThrowError() {
//        product.setCategory(null);
//        assertThrows(Error.class, () -> productService.save(product));
//    }
//
////    @Test
////    void givenDuplicatedNameAndCategory_whenSaveProduct_thenThrowError() {
////        categoryService.save(category);
////        List<Category> all = categoryRepository.findAll();
////        System.err.println("test");
////        Product sameNameAndCategoryProduct =
////                new Product("Product", "Description2", 2.0, "/image2", category);
////        assertThrows(Error.class, () -> productService.save(product));
////    }
//
////    @Test
////    void givenCategoryWithChildren_whenSaveProduct_thenThrowError(){
////
////    }
//
//}
