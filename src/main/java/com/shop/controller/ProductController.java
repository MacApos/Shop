package com.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.validation.product.group.sequence.CreateProductSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;

import java.util.List;
import java.util.Objects;

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
    public Product getProductById(@PathVariable Long id) throws JsonProcessingException {
        Product product = productService.findById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(product);
        return product;
    }

    @GetMapping("/by-category/{id}")
    public List<Product> getProductsByCategory(@PathVariable Long id){
        Category category = categoryService.findById(id);
        return productService.findAllByCategory(category);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Objects> create(@RequestBody @Validated(CreateProductSequence.class) Product product){
        productService.save(product);
        return ResponseEntity.ok().build();
    }
}