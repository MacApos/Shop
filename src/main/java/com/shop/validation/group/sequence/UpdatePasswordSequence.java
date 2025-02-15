package com.shop.validation.group.sequence;

import com.shop.validation.group.ResetPassword;
import jakarta.validation.GroupSequence;

@GroupSequence({ExistsSequence.class, ResetPassword.class})
public interface UpdatePasswordSequence {
}
