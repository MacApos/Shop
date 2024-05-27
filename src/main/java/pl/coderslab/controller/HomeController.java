package pl.coderslab.controller;

import jakarta.persistence.EntityManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.CustomUser;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.UserService;

import java.util.*;


@Controller
public class HomeController {
    private final UserService userService;
    private final CategoryService categoryService;

    public HomeController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public void createCategories() {
        List<List<String>> categories = new ArrayList<>(List.of(
                List.of("Artykuły domowe"),
                List.of("Ubrania"),
                List.of("AInne"),
                List.of("Dywany", "Artykuły"),
                List.of("Kolorowe dywany", "Dywany"),
                List.of("Jednokolorowe dywany", "Dywany"),
                List.of("Jednokolorowe dywany", "Dywany"),
                List.of("Inne dywany", "Artykuły"),
                List.of("Różne dywany", "Inne d"),
                List.of("Swetry", "Ubran")
        ));

        for (List<String> category : categories) {
            boolean isChild = category.size() > 1;
            String name = category.get(0);
            Category parent = isChild ? categoryService.findByNameLike(category.get(1)) : null;
            Category newCategory = isChild ? new Category(name, parent) : new Category(name);

            try {
                categoryService.save(newCategory);
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @RequestMapping("/")
    public String home(Model model) {
        createCategories();
        LinkedHashMap<Category, Object> all = categoryService.findAll();
        model.addAttribute("categories", all);
        return "home";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "accessDenied";
    }
}