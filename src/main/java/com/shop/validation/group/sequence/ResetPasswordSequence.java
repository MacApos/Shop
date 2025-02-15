package com.shop.validation.group.sequence;

import com.shop.validation.group.ResetPassword;
import jakarta.validation.GroupSequence;

@GroupSequence({ResetPassword.class})
public interface ResetPasswordSequence {
}
