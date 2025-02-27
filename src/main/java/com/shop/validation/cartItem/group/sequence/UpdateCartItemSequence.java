package com.shop.validation.cartItem.group.sequence;

import com.shop.validation.cartItem.group.database.CartItemExistsGroup;
import com.shop.validation.cartItem.group.defaults.UpdateCartItemDefaults;
import jakarta.validation.GroupSequence;

@GroupSequence({UpdateCartItemDefaults.class, CartItemExistsGroup.class})
public class UpdateCartItemSequence {
}