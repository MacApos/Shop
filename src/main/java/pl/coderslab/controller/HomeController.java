package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;
import pl.coderslab.service.UserService;

import java.util.*;


@Controller
public class HomeController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    public void createCategories() {
        List<List<String>> categories = new ArrayList<>(List.of(
                List.of("Artykuły domowe"),
                List.of("Ubrania"),
                List.of("Inne"),
                List.of("Pozostałe"),
                List.of("Dywany", "Artykuły domowe"),
                List.of("Kolorowe dywany", "Dywany"),
                List.of("Jednokolorowe dywany", "Dywany"),
                List.of("Jednokolorowe dywany", "Dywany"),
                List.of("Inne dywany", "Artykuły domowe"),
                List.of("Różne dywany", "Inne dywany"),
                List.of("Czapki", "Ubrania")
        ));

        for (List<String> category : categories) {
            boolean isChild = category.size() > 1;
            String name = category.get(0);
            Category parent = isChild ? categoryService.findByName(category.get(1)) : null;
            Category newCategory = new Category(name, parent);

            try {
                categoryService.save(newCategory);
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void createProducts() {
        List<List<String>> products = List.of(
                List.of("Czapka 1", "Czapki"),
                List.of("Czapka 2", "Czapki"),
                List.of("Czerwony dywan", "Kolorowe dywany"),
                List.of("Zielony dywan", "Kolorowe dywany"),
                List.of("Dywan wzór 1", "Jednokolorowe dywany"),
                List.of("Dywan wzór 2", "Jednokolorowe dywany"),
                List.of("Dywan do salonu", "Różne dywany"),
                List.of("Dywan dla dziecka", "Różne dywany"),
                List.of("Torba 1", "Inne"),
                List.of("Torba 2", "Inne")
        );

        for (List<String> product : products) {
            Product newProduct = new Product(
                    product.get(0),
                    "Lorem ipsum",
                    10.0,
                    "crochet-hat.jpg",
                    categoryService.findByName(product.get(1)));

            try {
                productService.save(newProduct);
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @RequestMapping("/")
    public String home(Model model) {
        createCategories();
        createProducts();
        LinkedHashMap<Category, Object> all = categoryService.getHierarchyMap();
        model.addAttribute("categories", all);

        return "home";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "accessDenied";
    }
}