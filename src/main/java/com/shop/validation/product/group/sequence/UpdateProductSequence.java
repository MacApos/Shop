package com.shop.validation.product.group.sequence;

import com.shop.validation.category.group.database.CategoryExistsGroup;
import com.shop.validation.category.group.database.CategoryHasNoChildGroup;
import com.shop.validation.product.group.database.ProductExistsGroup;
import com.shop.validation.product.group.defaults.UpdateProductDefaults;
import jakarta.validation.GroupSequence;

@GroupSequence({UpdateProductDefaults.class, ProductExistsGroup.class,
        CategoryExistsGroup.class, CategoryHasNoChildGroup.class})
public interface UpdateProductSequence {
}
