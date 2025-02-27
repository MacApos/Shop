package com.shop.validation.cartItem.group.sequence;

import com.shop.validation.cartItem.group.database.CartItemExistsGroup;
import com.shop.validation.cartItem.group.defaults.DeleteCartItemDefaults;
import jakarta.validation.GroupSequence;

@GroupSequence({DeleteCartItemDefaults.class, CartItemExistsGroup.class})
public class DeleteCartItemSequence {
}