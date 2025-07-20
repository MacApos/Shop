package com.shop.validation.cartItem.validator;

import com.shop.model.CartItem;
import com.shop.model.User;
import com.shop.service.AuthenticationService;
import com.shop.service.CartItemService;
import com.shop.validation.cartItem.annotation.CartItemExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemExistsValidator implements ConstraintValidator<CartItemExists, CartItem> {
    private final AuthenticationService authenticationService;
    private final CartItemService cartItemService;

    @Override
    public boolean isValid(CartItem cartItem, ConstraintValidatorContext constraintValidatorContext) {
        if (cartItem == null || !cartItemService.existsById(cartItem.getId())) {
            return false;
        }
        User user = authenticationService.getAuthenticatedUser();
        return cartItemService.existsByIdAndUser(cartItem.getId(), user);
    }
}
