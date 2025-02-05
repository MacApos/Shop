package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.ResetPassword;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, ResetPassword.class})
public interface DefaultAndResetPassword {
}
