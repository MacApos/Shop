package com.shop.validation.user.group.sequence;

import com.shop.validation.user.group.expensive.ResetPassword;
import jakarta.validation.GroupSequence;

@GroupSequence({UserExistsSequence.class, ResetPassword.class})
public interface UpdatePasswordSequence {
}
