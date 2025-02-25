package com.shop.validation.user.group.sequence;

import com.shop.validation.user.group.expensive.UpdateUser;
import com.shop.validation.user.group.defaults.DefaultUpdateUser;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultUpdateUser.class, UpdateUser.class})
public interface UpdateUserSequence {
}
