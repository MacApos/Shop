package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.database.UniqueCategoryGroup;
import com.shop.validation.category.group.defaults.CreateCategoryDefaults;
import com.shop.validation.category.group.database.ParentExistsByIdGroup;
import com.shop.validation.category.group.defaults.ValidNameGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({CreateCategoryDefaults.class, ValidNameGroup.class,
        ParentExistsByIdGroup.class, UniqueCategoryGroup.class})
public interface CreateCategorySequence {
}
