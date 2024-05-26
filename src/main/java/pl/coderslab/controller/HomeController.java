package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.CustomUser;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.UserService;

import java.util.HashMap;
import java.util.List;


@Controller
public class HomeController {
    private final UserService userService;
    private final CategoryService categoryService;

    public HomeController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/")
    public String home(@AuthenticationPrincipal CustomUser userDetails, Model model) {
        HashMap<String, Object> all = categoryService.findAll();
        model.addAttribute("categories", all);
        return "home";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "accessDenied";
    }
}