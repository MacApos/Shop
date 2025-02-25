package com.shop.validation.user.group.sequence;

import com.shop.validation.user.group.defaults.DefaultEmail;
import com.shop.validation.user.group.defaults.DefaultToken;
import com.shop.validation.user.group.expensive.UserExists;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultEmail.class, DefaultToken.class, UserExists.class})
public interface UserExistsSequence {
}
