package com.shop.validation.groups;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, CheckFirst.class})
public interface DefaultFirst {
}
