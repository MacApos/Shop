package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.entity.CustomUser;
import pl.coderslab.entity.User;
import pl.coderslab.service.UserService;


@Controller
public class HomeController {
    UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home(@AuthenticationPrincipal CustomUser userDetails, Model model) {
        if(userDetails!=null){
            User user = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("isAdmin", userService.isUserAdmin(user));
        }
        return "home";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "accessDenied";
    }
}