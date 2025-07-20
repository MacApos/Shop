package com.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import com.shop.model.Category;
import com.shop.service.CategoryService;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public Set<Category> home() {
        return categoryService.getHierarchy();
    }

    @PostMapping("/test")
    public HashMap<String, Object> test(@RequestBody Map<String, String> object) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", 1);
        hashMap.put("isTest", true);
        return hashMap;
    }

    @GetMapping("/alternatives/{id}")
    public Set<Category> alternatives(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return categoryService.getAlternativeParents(category);
    }

    private Double measureTime(Runnable runnable) {
        long t1 = System.nanoTime();
        runnable.run();
        long t2 = System.nanoTime();
        return (t2 - t1) / 1_000_000.0;
    }

    @GetMapping("/test-hierarchy")
    public Map<String, Double> hierarchyPerformanceTest() {
        Double original = measureTime(categoryService::getHierarchyV3);
        Double dequeue = measureTime(categoryService::getHierarchy);
        Double list = measureTime(categoryService::getHierarchyV2);
        HashMap<String, Double> hashMap = new HashMap<>();
        hashMap.put("original", original);
        hashMap.put("dequeue", dequeue);
        hashMap.put("list", list);
        return hashMap;
    }

    @GetMapping("/test-alternatives")
    public Map<String, Double> alternativesPerformanceTest() {
        Category category = categoryService.findById(5L);
        Double dequeue = measureTime(() -> categoryService.getAlternativeParents(category));
        Double list = measureTime(() -> categoryService.getAlternativeParentsV2(category));
        HashMap<String, Double> hashMap = new HashMap<>();
        hashMap.put("list", list);
        hashMap.put("dequeue", dequeue);
        return hashMap;
    }

}