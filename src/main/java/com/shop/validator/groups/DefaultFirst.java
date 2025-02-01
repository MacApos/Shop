package com.shop.validator.groups;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, CheckFirst.class})
public interface DefaultFirst {
}
