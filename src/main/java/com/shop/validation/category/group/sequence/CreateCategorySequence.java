package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.defaults.CreateCategoryDefaults;
import com.shop.validation.category.group.expensive.ParentExistsByIdGroup;
import com.shop.validation.category.group.expensive.UniqueCategoryGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({CreateCategoryDefaults.class, ParentExistsByIdGroup.class, UniqueCategoryGroup.class})
public interface CreateCategorySequence {
}
