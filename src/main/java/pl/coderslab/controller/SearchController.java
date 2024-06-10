package pl.coderslab.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.CartItem;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.entity.User;
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
    public String searchController(HttpServletRequest request, Model model, @AuthenticationPrincipal User user) {
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
            if (product == null) {
                return "error/404";
            }
            List<Category> parents = categoryService.getParentsLine(product.getCategory());
            CartItem cartItem = new CartItem();
            model.addAttribute("product", product);
            model.addAttribute("parents", parents);
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