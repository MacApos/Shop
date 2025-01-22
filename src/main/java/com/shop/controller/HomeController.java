package com.shop.controller;

import com.shop.repository.CategoryRepository;
import com.shop.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    private final EmailService emailService;

    @RequestMapping("/")
    public Set<Category> home() {
        return categoryService.getHierarchy();
    }

    @RequestMapping("/send")
    public String sendEmail() {
        emailService.sendSimpleMessage();
        return "sent";
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