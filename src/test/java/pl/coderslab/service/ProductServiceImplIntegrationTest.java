package pl.coderslab.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.repository.ProductRepository;
import pl.coderslab.service.impl.CategoryServiceImpl;
import pl.coderslab.service.impl.ProductServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplIntegrationTest {
    @Mock
    private CategoryServiceImpl categoryService;
    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

//    private Category category;
//    private Product product;
//    private Product sameNameAndCategoryProduct;
//
//    @BeforeAll
//    static void initProductService() {
//    }
//
    @BeforeEach
    void initProduct() {
        productService = new ProductServiceImpl(categoryService, productRepository);
//        category = new Category();
//        product = new Product("Product", "Description1", 1.0, "/image1", category);
//        sameNameAndCategoryProduct = new Product(product.getName(), "Description2", 2.0,
//                "/image2", category);
    }

    @Test
    void givenNullCategory_whenSaveProduct_thenThrowError() {
        Product product = new Product("Product", "Description1", 1.0, "/image1", null);
        assertThrows(Error.class, () -> productService.save(product));
    }

//    @Test
//    void givenDuplicatedNameAndCategory_whenSaveProduct_thenThrowError() {
//        System.out.println(productService);
//        productService.save(product);
//        assertThrows(Error.class, () -> productService.save(sameNameAndCategoryProduct));
//    }

//    @Test
//    void givenCategoryWithChildren_whenSaveProduct_thenThrowError(){
//
//    }

}
