package com.shop.validation.user.group.sequence;

import com.shop.validation.user.group.expensive.CreateUser;
import com.shop.validation.user.group.defaults.DefaultCreateUser;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultCreateUser.class, CreateUser.class})
public interface CreateUserSequence {
}
