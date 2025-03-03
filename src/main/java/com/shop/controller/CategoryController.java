package com.shop.controller;

import com.shop.entity.Category;
import com.shop.service.CategoryService;
import com.shop.validation.category.group.sequence.CreateCategorySequence;
import com.shop.validation.category.group.sequence.DeleteCategorySequence;
import com.shop.validation.category.group.sequence.UpdateCategorySequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

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

    @PostMapping("/admin/create")
    public ResponseEntity<Object> create(@RequestBody @Validated(CreateCategorySequence.class) Category category) {
        categoryService.save(category);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/update")
    public ResponseEntity<Object> update(@RequestBody @Validated(UpdateCategorySequence.class) Category category) {
        categoryService.update(category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<Object> delete(@RequestBody @Validated(DeleteCategorySequence.class) Category category) {
        categoryService.delete(category);
        return ResponseEntity.ok().build();
    }
}
