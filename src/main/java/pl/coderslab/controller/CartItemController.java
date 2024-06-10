package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartItemController {
    @RequestMapping("/cartitem")
    @ResponseBody
    public String cartitem() {
        return "";
    }
}