package com.shop.validation.product.group.sequence;

import com.shop.validation.product.group.database.ProductExistsGroup;
import com.shop.validation.product.group.defaults.DeleteProductDefaults;
import jakarta.validation.GroupSequence;

@GroupSequence({DeleteProductDefaults.class, ProductExistsGroup.class})
public interface DeleteProductSequence {
}
