package com.shop.validation.group.sequence;

import com.shop.validation.group.Update;
import com.shop.validation.group.defaults.DefaultUpdateUser;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultUpdateUser.class, Update.class})
public interface UpdateSequence {
}
