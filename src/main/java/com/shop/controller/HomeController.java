package com.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.entity.Category;
import com.shop.service.CategoryService;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;

    @RequestMapping("/")
    public Set<Category> home() {
        return categoryService.getHierarchy();
    }

    @RequestMapping("/alternatives/{id}")
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

    @RequestMapping("/test-hierarchy")
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

    @RequestMapping("/test-alternatives")
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