package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.database.CategoryExistsByIdGroup;
import com.shop.validation.category.group.defaults.UpdateCategoryDefaults;
import com.shop.validation.category.group.defaults.ValidNameGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({UpdateCategoryDefaults.class, ValidNameGroup.class,
        CategoryExistsByIdGroup.class, CategoryDatabaseSequence.class})
public interface UpdateCategorySequence {
}
