package pl.coderslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.findById(id);
    }

    @GetMapping("/by-category/{id}")
    public List<Product> getProductsByCategory(@PathVariable Long id){
        Category category = categoryService.findById(id);
        return productService.findAllByCategory(category);
    }
}