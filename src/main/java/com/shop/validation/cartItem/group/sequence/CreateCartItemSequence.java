package com.shop.validation.cartItem.group.sequence;

import com.shop.validation.cartItem.group.defaults.CreateCartItemDefaults;
import com.shop.validation.category.group.expensive.CategoryExistsByIdGroup;
import com.shop.validation.product.group.ProductExistsByIdGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({CreateCartItemDefaults.class, ProductExistsByIdGroup.class, CategoryExistsByIdGroup.class})
public class CreateCartItemSequence {
}