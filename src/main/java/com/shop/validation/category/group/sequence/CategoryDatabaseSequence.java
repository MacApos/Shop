package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.database.ParentExistsByIdGroup;
import com.shop.validation.category.group.database.ParentHasNoProductsGroup;
import com.shop.validation.category.group.database.UniqueCategoryGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({ParentExistsByIdGroup.class, ParentHasNoProductsGroup.class, UniqueCategoryGroup.class})
public interface CategoryDatabaseSequence {
}
