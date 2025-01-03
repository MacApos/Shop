package pl.coderslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.service.impl.CategoryService;
import pl.coderslab.service.impl.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/products/category/{id}")
    public List<Product> getProductsByCategory(@PathVariable Long id){
        Category category = categoryService.findById(id);
        if(category==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return productService.recursiveFindAllByCategory(category);
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id){
        Product product = productService.findById(id);
        if( product==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return product;
    }
}