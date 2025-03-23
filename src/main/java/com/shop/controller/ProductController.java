package com.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.validation.product.group.sequence.CreateProductSequence;
import com.shop.validation.product.group.sequence.DeleteProductSequence;
import com.shop.validation.product.group.sequence.UpdateProductSequence;
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
    public Product getProductById(@PathVariable Long id)  {
        return productService.findById(id);
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

    @PutMapping("/admin/update")
    public ResponseEntity<Objects> update(@RequestBody @Validated(UpdateProductSequence.class) Product product){
        productService.update(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<Objects> delete(@RequestBody @Validated(DeleteProductSequence.class) Product product){
        productService.delete(product);
        return ResponseEntity.ok().build();
    }
}