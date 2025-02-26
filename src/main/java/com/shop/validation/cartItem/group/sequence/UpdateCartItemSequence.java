package com.shop.validation.cartItem.group.sequence;

import com.shop.validation.cartItem.annotation.CartItemExists;
import com.shop.validation.cartItem.group.defaults.CreateCartItemDefaults;
import com.shop.validation.cartItem.group.defaults.UpdateCartItemDefaults;
import com.shop.validation.category.group.expensive.CategoryExistsByIdGroup;
import com.shop.validation.product.group.ProductExistsByIdGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({UpdateCartItemDefaults.class, CartItemExists.class})
public class UpdateCartItemSequence {
}