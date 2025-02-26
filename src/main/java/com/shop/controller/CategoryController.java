package com.shop.controller;

import com.shop.validation.category.group.sequence.CreateCategorySequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/hierarchy")
    @ResponseBody
    public Set<Category> getCategoriesHierarchy() {
        return categoryService.getHierarchy();
    }

    @GetMapping("/hierarchy-flat")
    @ResponseBody
    public List<Category> getCategoriesHierarchyFlat() {
        return categoryService.getHierarchyFlat();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/parents/{id}")
    public List<Category> getCategoryParents(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return categoryService.findParents(category);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> create(@RequestBody @Validated(CreateCategorySequence.class) Category category) {
        return ResponseEntity.ok().build();
    }
}