package com.shop.validation.category.group.sequence;

import com.shop.validation.category.group.defaults.CreateCategoryDefaults;
import com.shop.validation.category.group.defaults.ValidNameGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({CreateCategoryDefaults.class, ValidNameGroup.class, CategoryDatabaseSequence.class})
public interface CreateCategorySequence {
}
