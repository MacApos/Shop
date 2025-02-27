package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.database.CategoryExistsByIdGroup;
import com.shop.validation.category.group.database.ParentExistsByIdGroup;
import com.shop.validation.category.group.database.ParentHasChildrenGroup;
import com.shop.validation.category.group.defaults.UpdateCategoryDefaults;
import jakarta.validation.GroupSequence;

@GroupSequence({UpdateCategoryDefaults.class, CategoryExistsByIdGroup.class,
        ParentExistsByIdGroup.class, ParentHasChildrenGroup.class})
public interface UpdateCategorySequence {
}
