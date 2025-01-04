package pl.coderslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.entity.Category;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.service.UserService;
import pl.coderslab.service.impl.CategoryService;
import pl.coderslab.service.impl.ProductService;

import java.util.*;


@RestController
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @RequestMapping("/")
    public Set<Category> home() {
        return categoryService.getHierarchy();
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "accessDenied";
    }
}