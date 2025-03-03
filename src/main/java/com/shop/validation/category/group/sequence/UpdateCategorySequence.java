package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.database.CategoryExistsGroup;
import com.shop.validation.category.group.database.ParentIsNotItselfChildGroup;
import com.shop.validation.category.group.defaults.UpdateCategoryDefaults;
import com.shop.validation.category.group.defaults.ValidNameGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({UpdateCategoryDefaults.class, ValidNameGroup.class, CategoryExistsGroup.class,
        CategoryDatabaseSequence.class, ParentIsNotItselfChildGroup.class})
public interface UpdateCategorySequence {
}
