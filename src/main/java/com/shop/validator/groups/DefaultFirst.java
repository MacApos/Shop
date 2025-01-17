package com.shop.validator.groups;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, NewEntity.class})
public interface DefaultFirst {
}
