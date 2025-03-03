package com.shop.controller;

import com.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @RequestMapping("/")
    public Set<Category> home() {
        return categoryService.getHierarchy();
    }

    @RequestMapping("/v2")
    public ResponseEntity<Double> homeV2() {
        long t1 = System.nanoTime();
        Set<Category> hierarchy = categoryService.getHierarchyV2();
        long t2 = System.nanoTime();
        double time = (t2 - t1) / 1_000_000.0;
        return ResponseEntity.ok(time);
    }

    @RequestMapping("/ok")
    public Category ok() {
        Map<String, String> response = Map.of("response", "ok");
        Category category = new Category("category");
        return category;
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "accessDenied";
    }
}