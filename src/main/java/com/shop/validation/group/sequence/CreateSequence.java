package com.shop.validation.group.sequence;

import com.shop.validation.group.Create;
import com.shop.validation.group.defaults.DefaultCreateUser;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultCreateUser.class, Create.class})
public interface CreateSequence {
}
