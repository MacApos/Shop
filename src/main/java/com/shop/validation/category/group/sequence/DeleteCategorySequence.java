package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.database.CategoryExistsGroup;
import com.shop.validation.category.group.defaults.DeleteCategoryDefaults;
import jakarta.validation.GroupSequence;

@GroupSequence({DeleteCategoryDefaults.class, CategoryExistsGroup.class})
public interface DeleteCategorySequence {
}
