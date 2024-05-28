package pl.coderslab.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;

import java.util.List;


@Controller
public class SearchController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public SearchController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @RequestMapping("/search/**")
    public String searchController(HttpServletRequest request, Model model) {
        String path = request.getRequestURI().replace("/search/", "");
        Category category = categoryService.findByPath(path);
        if (category == null) {
            return "error/404";
        }
        List<Product> products = productService.findAllByCategory(category);
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping("/search/all")
    public String searchAll(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

}