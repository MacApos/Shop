package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.User;
import com.shop.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@PreAuthorize("hasRole('ROLE_USER')")
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

    @PostMapping("/add")
    public void add(@RequestBody @Validated CartItem cartItem) {
        User user = authenticationService.getAuthenticatedUser();

        Cart cart = cartService.findByUser(user);
        CartItem existingCartItem = null;

        if (cart == null) {
            cart = new Cart();
            cartService.save(cart);
        } else {
            existingCartItem = cartItemService.findByProductAndCart(cart, cartItem.getProduct());
        }

        if (existingCartItem == null) {
            cartItem.setCart(cart);
        } else {
            existingCartItem.addQuantity(cartItem.getQuantity());
            cartItem = existingCartItem;
        }
        cartItemService.save(cartItem);

//        Cart cart = new Cart();
//        CartItem existingCartItem = null;
//        int quantity = cartItem.getQuantity();
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
//
////        v2
//        if (principal == null) {
//            cart = (Cart) session.getAttribute("cart");
//            List<CartItem> cartItems = new ArrayList<>();
//            int index = -1;
//            if (cart == null) {
//                cart = new Cart();
//            } else {
//                cartItems = cart.getCartItems();
//                index = cartItems.indexOf(cartItem);
//            }
//            if (index == -1) {
//                cartItem.setCart(cart);
//            } else {
//                existingCartItem = cartItems.get(index);
//                existingCartItem.addQuantity(quantity);
//            }
//            session.setAttribute("cart", cart);
//        } else {
//            User user = userService.findByEmail(principal.getName());
//            cart = cartService.findByUser(user);
//            if (cart == null) {
//                cart = new Cart();
//                cartService.save(cart);
//            } else {
//                existingCartItem = cartItemService.findByProductAndCart(cart, cartItem.getProduct());
//            }
//            if (existingCartItem == null) {
//                cartItem.setCart(cart);
//            } else {
//                existingCartItem.addQuantity(quantity);
//                cartItem = existingCartItem;
//            }
//            cartItemService.save(cartItem);
//        }
//
////        v3
//        boolean isAuthenticated = principal != null;
//        if (isAuthenticated) {
//            User user = userService.findByEmail(principal.getName());
//            cart = cartService.findByUser(user);
//        } else {
//            cart = (Cart) session.getAttribute("cart");
//        }
//
//        if (cart == null) {
//            cart = new Cart();
//            if (isAuthenticated) {
//                cartService.save(cart);
//            }
//        } else if (isAuthenticated) {
//            existingCartItem = cartItemService.findByProductAndCart(cart, cartItem.getProduct());
//        } else {
//            List<CartItem> items = cart.getCartItems();
//            int i = items.indexOf(cartItem);
//            existingCartItem = i > -1 ? items.get(i) : null;
//        }
//
//        if (existingCartItem == null) {
//            cartItem.setCart(cart);
//        } else {
//            existingCartItem.addQuantity(quantity);
//            cartItem = existingCartItem;
//        }
//
//        if (isAuthenticated) {
//            cartItemService.save(cartItem);
//        } else {
//            session.setAttribute("cart", cart);
//        }
    }

    @GetMapping("/update")
    public void update(Principal principal, HttpSession session) {
//        @RequestBody @Validated CartItem cartItem,x
        Cart cart = cartService.findByUserEmail("user@gmail.com");

//        if (principal == null) {
//             cart = (Cart) session.getAttribute("cart");
//        } else {
//            cartService.findByUser()
//        }
//        cartItemService.findByProductAndCart();
        User user = authenticationService.getAuthenticatedUser();
    }
}
