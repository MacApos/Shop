package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {
    @RequestMapping("/cart")
    @ResponseBody
    public String cart() {
        return "howdy";
    }

}