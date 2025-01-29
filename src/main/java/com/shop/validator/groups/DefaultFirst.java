package com.shop.validator.groups;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, CheckInOrder.class})
public interface DefaultFirst {
}
