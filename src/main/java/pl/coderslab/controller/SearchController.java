package pl.coderslab.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.repository.ProductRepository;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;

import java.util.List;


@Controller
@RequestMapping(CategoryService.SEARCH_PATH)
public class SearchController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public SearchController(CategoryService categoryService, ProductService productService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @RequestMapping("/**")
    public String searchController(HttpServletRequest request, Model model) {
        String path = request.getRequestURI();
        String[] split = categoryService.splitPathAroundProduct(path);
        path = split[0];

        Category category = categoryService.findByPath(path);
        if (category == null) {
            return "error/404";
        }
        if (split.length > 1) {
            String productPath = split[1];
            Product product = productService.findByPathAndCategory(productPath, category);
            Product byCategory = productService.findByCategory(category);
            if (product == null) {
                return "error/404";
            }
            model.addAttribute("product", product);
            return "product";
        }
        List<Product> products = productService.findAllByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "allProducts";
    }

    @RequestMapping("/all")
    public String searchAll(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("category", "Wszystkie");
        model.addAttribute("products", products);
        return "allProducts";
    }

}