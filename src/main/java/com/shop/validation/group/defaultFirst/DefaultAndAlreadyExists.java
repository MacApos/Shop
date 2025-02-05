package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.AlreadyExists;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, AlreadyExists.class})
public interface DefaultAndAlreadyExists {
}
