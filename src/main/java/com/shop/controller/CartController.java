package com.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.User;
import com.shop.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
//@PreAuthorize("hasRole('ROLE_USER')")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @GetMapping("/all")
    public List<CartItem> cart(Principal principal, HttpSession session) {
        Cart cart;
        if (principal == null) {
            cart = (Cart) session.getAttribute("cart");
        } else {
            User user = userService.findByEmail(principal.getName());
            cart = cartService.findByUser(user);
        }
        if (cart == null) {
            return List.of();
        }
        return principal == null ? cart.getCartItems() : cartItemService.findByCart(cart);
    }

    //    @PostMapping("/add")
    @GetMapping("/add")
    public void add(Principal principal,
                    @RequestBody @Validated CartItem cartItem,
                    HttpSession session) {
        Cart sessionCart = new Cart();
        CartItem cartItem1 = new CartItem(1, productService.findByName("Czapka 1"), sessionCart);
        CartItem cartItem2 = new CartItem(1, productService.findByName("Czapka 2"), sessionCart);
        CartItem cartItem3 = new CartItem(1, productService.findByName("Czerwony dywan"), sessionCart);
        sessionCart.setCartItems(List.of(cartItem1, cartItem2, cartItem3));
        String s;
        try {
            s = objectMapper.writeValueAsString(sessionCart);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        session.setAttribute("sessionCart", sessionCart);
        Object attribute = (Cart) session.getAttribute("sessionCart");

        Cart cart = new Cart();
        CartItem existingCartItem = null;
        int quantity = cartItem.getQuantity();
////        v1
//        if (principal == null) {
//            cart = (Cart) session.getAttribute("cart");
//
//            if (cart == null) {
//                cart = new Cart();
//                session.setAttribute("cart", cart);
//            } else {
//                List<CartItem> cartItems = cart.getCartItems();
//                int index = cartItems.indexOf(cartItem);
//                if (index > -1) {
//                    existingCartItem = cartItems.get(index);
//                    existingCartItem.addQuantity(quantity);
//                    return;
//                }
//            }
//            cartItem.setCart(cart);
//        } else {
//            User user = userService.findByEmail(principal.getName());
//            cart = cartService.findByUser(user);
//
//            if (cart == null) {
//                cart = new Cart();
//                cartService.save(cart);
//            } else {
//                existingCartItem = cartItemService.findByProductAndCart(cart, quantity);
//            }
//
//            if (existingCartItem == null) {
//                cartItem.setCart(cart);
//            } else {
//                existingCartItem.addQuantity(quantity);
//                cartItem = existingCartItem;
//            }
//            cartItemService.save(cartItem);
//        }

//        v2
        List<CartItem> cartItems = new ArrayList<>();
        int index = -1;
        if (principal == null) {
            cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            } else {
                cartItems = cart.getCartItems();
                index = cartItems.indexOf(cartItem);
            }
            if (index == -1) {
                cartItem.setCart(cart);
            } else {
                existingCartItem = cartItems.get(index);
                existingCartItem.addQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        } else {
            User user = userService.findByEmail(principal.getName());
            cart = cartService.findByUser(user);

            if (cart == null) {
                cart = new Cart();
                cartService.save(cart);
            } else {
                existingCartItem = cartItemService.findByProductAndCart(cart, cartItem.getProduct());
            }

            if (existingCartItem == null) {
                cartItem.setCart(cart);
            } else {
                existingCartItem.addQuantity(quantity);
                cartItem = existingCartItem;
            }
            cartItemService.save(cartItem);
        }

//        v3
        boolean isAuthenticated = principal != null;
        if (isAuthenticated) {
            User user = userService.findByEmail(principal.getName());
            cart = cartService.findByUser(user);
        } else {
            cart = (Cart) session.getAttribute("cart");
        }

        if (cart == null) {
            cart = new Cart();
            if (isAuthenticated) {
                cartService.save(cart);
            }
        } else if (isAuthenticated) {
            existingCartItem = cartItemService.findByProductAndCart(cart, cartItem.getProduct());
        } else {
            List<CartItem> items = cart.getCartItems();
            int i = items.indexOf(cartItem);
            existingCartItem = i > -1 ? items.get(i) : null;
        }

        if (existingCartItem == null) {
            cartItem.setCart(cart);
        } else {
            existingCartItem.addQuantity(quantity);
            cartItem = existingCartItem;
        }

        if (isAuthenticated) {
            cartItemService.save(cartItem);
        } else {
            session.setAttribute("cart", cart);
        }
    }

    @GetMapping("/update")
    public void update(Principal principal, HttpSession session) {
//        @RequestBody @Validated CartItem cartItem,x
        Cart cart = cartService.findByEmail("user@gmail.com");

//        if (principal == null) {
//             cart = (Cart) session.getAttribute("cart");
//        } else {
//            cartService.findByUser()
//        }
//        cartItemService.findByProductAndCart();
        User user = authenticationService.getAuthenticatedUser();
    }
}
