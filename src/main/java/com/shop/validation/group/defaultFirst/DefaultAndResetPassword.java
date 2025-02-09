package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.DefaultPassword;
import com.shop.validation.group.ResetPassword;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultPassword.class, ResetPassword.class})
public interface DefaultAndResetPassword {
}
