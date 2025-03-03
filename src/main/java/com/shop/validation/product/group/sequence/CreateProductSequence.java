package com.shop.validation.product.group.sequence;

import com.shop.validation.category.group.database.CategoryExistsGroup;
import com.shop.validation.category.group.database.CategoryHasNoChildGroup;
import com.shop.validation.product.group.defaults.CreateProductDefaults;
import jakarta.validation.GroupSequence;

@GroupSequence({CreateProductDefaults.class, CategoryExistsGroup.class, CategoryHasNoChildGroup.class})
public interface CreateProductSequence {
}
