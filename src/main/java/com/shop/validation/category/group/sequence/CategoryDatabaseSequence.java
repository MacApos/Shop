package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.database.ParentExistsGroup;
import com.shop.validation.category.group.database.ParentHasNoProductsGroup;
import com.shop.validation.category.group.database.UniqueCategoryGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({ParentExistsGroup.class, ParentHasNoProductsGroup.class, UniqueCategoryGroup.class})
public interface CategoryDatabaseSequence {
}
