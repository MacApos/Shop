package pl.coderslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Category;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;

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


}