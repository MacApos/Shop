package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.Exists;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, Exists.class})
public interface DefaultAndExists {
}
