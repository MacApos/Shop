package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.DefaultFirst;
import com.shop.validation.group.ResetPassword;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultFirst.class, ResetPassword.class})
public interface DefaultAndResetPassword {
}
