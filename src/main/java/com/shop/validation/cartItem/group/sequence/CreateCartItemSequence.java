package com.shop.validation.cartItem.group.sequence;

import com.shop.validation.cartItem.group.defaults.CreateCartItemDefaults;
import com.shop.validation.category.group.database.CategoryExistsGroup;
import com.shop.validation.product.group.database.ProductExistsGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({CreateCartItemDefaults.class, ProductExistsGroup.class, CategoryExistsGroup.class})
public interface CreateCartItemSequence {
}