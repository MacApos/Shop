package com.shop.validation.group.sequence;

import com.shop.validation.group.UpdateUser;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, UpdateUser.class})
public interface UpdateSequence {
}
