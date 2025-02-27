package com.shop.controller;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.User;
import com.shop.service.*;
import com.shop.validation.cartItem.group.defaults.UpdateCartItemDefaults;
import com.shop.validation.cartItem.group.sequence.CreateCartItemSequence;
import com.shop.validation.cartItem.group.sequence.DeleteCartItemSequence;
import com.shop.validation.cartItem.group.sequence.UpdateCartItemSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-item")
@PreAuthorize("hasRole('ROLE_USER')")
public class CartItemController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping("/all")
    public List<CartItem> getAll() {
        User user = authenticationService.getAuthenticatedUser();
        Cart cart = cartService.findByUser(user);

        if (cart == null) {
            return List.of();
        }
        return cartItemService.findByCart(cart);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Validated(CreateCartItemSequence.class) CartItem cartItem) {
        User user = authenticationService.getAuthenticatedUser();
        Cart cart = cartService.findByUser(user);
        CartItem existingCartItem = null;

        if (cart == null) {
            cart = new Cart(user);
            cartService.save(cart);
        } else {
            existingCartItem = cartItemService.findByProductAndCart(cartItem.getProduct(), cart);
        }

        if (existingCartItem == null) {
            cartItem.setCart(cart);
        } else {
            existingCartItem.addQuantity(cartItem.getQuantity());
            cartItem = existingCartItem;
        }
        cartItemService.save(cartItem);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(Principal principal,
                                         @RequestBody @Validated(UpdateCartItemSequence.class) CartItem cartItem) {
        User user = userService.findByEmail(principal.getName());
        CartItem existingCartItem = cartItemService.findByIdAndUser(cartItem.getId(), user);
        existingCartItem.setQuantity(cartItem.getQuantity());
        cartItemService.save(existingCartItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(Principal principal, @RequestBody @Validated(DeleteCartItemSequence.class) CartItem cartItem) {
        User user = userService.findByEmail(principal.getName());
        CartItem existingCartItem = cartItemService.findByIdAndUser(cartItem.getId(), user);
        cartItemService.delete(existingCartItem);
        return ResponseEntity.ok().build();
    }
}
