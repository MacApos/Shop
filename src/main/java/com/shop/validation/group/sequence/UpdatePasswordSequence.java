package com.shop.validation.group.sequence;

import com.shop.validation.group.defaults.DefaultEmail;
import com.shop.validation.group.defaults.DefaultPassword;
import com.shop.validation.group.ResetPassword;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultEmail.class, DefaultPassword.class, ResetPassword.class})
public interface UpdatePasswordSequence {
}
