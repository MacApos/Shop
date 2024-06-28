package pl.coderslab.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.dto.CategoryDto;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;
import pl.coderslab.service.UserService;

import java.util.*;
import java.util.logging.Level;


@RestController
public class HomeController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public HomeController(UserService userService, CategoryService categoryService, ProductService productService, CategoryRepository categoryRepository) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping("/")
    public List<CategoryDto> home() {
        List<CategoryDto> hierarchyMap = categoryService.getHierarchyMap();
        return hierarchyMap;
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "accessDenied";
    }
}