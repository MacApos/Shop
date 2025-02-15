package com.shop.validation.group.sequence;

import com.shop.validation.group.*;
import com.shop.validation.group.defaults.DefaultNewEmail;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultNewEmail.class, UpdateEmail.class})
public interface UpdateEmailSequence {
}
