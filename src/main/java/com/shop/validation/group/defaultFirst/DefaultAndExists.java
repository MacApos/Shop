package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.DefaultFirst;
import com.shop.validation.group.Exists;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultFirst.class, Exists.class})
public interface DefaultAndExists {
}
