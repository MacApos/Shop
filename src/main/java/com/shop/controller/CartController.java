package com.shop.controller;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.User;
import com.shop.service.AuthenticationService;
import com.shop.service.CartItemService;
import com.shop.service.CartService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final AuthenticationService authenticationService;

    @GetMapping("/all")
    public List<CartItem> cart() {
        User user = authenticationService.getAuthenticatedUser();
        Cart cart = cartService.findByUser(user);
        if (cart == null) {
            return List.of();
        }
        return cartItemService.findByCart(cart);
    }

    @PostMapping("/add")
    public void add(@RequestBody @Validated CartItem cartItem) {
        User user = authenticationService.getAuthenticatedUser();
        Cart cart = cartService.findByUser(user);
        if (cart == null) {
            cart = new Cart(user);
            cartItem.setCart(cart);
            cartService.save(cart);
        }
        CartItem existingCartItem = cartItemService.findByProductAndCart(cart, cartItem.getProduct());
        if (existingCartItem == null) {
            cartItem.setCart(cart);
        } else {
            existingCartItem.addQuantity(cartItem.getQuantity());
            cartItem = existingCartItem;
        }
        cartItemService.save(cartItem);
    }

    @PostMapping("/update")
    public void update(@RequestBody @Validated CartItem cartItem) {
        User user = authenticationService.getAuthenticatedUser();


    }
}
