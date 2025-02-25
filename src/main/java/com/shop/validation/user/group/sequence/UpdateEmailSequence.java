package com.shop.validation.user.group.sequence;

import com.shop.validation.user.group.defaults.DefaultNewEmail;
import com.shop.validation.user.group.expensive.UpdateEmail;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultNewEmail.class, UpdateEmail.class})
public interface UpdateEmailSequence {
}
