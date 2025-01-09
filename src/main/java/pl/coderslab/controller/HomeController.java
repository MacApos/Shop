package pl.coderslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.entity.Category;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @RequestMapping("/")
    public Set<Category> home(Authentication authentication) {
        authentication.getAuthorities();
        return categoryService.getHierarchy();
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