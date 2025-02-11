package com.shop.validation.group.sequence;

import com.shop.validation.group.*;
import com.shop.validation.group.defaults.DefaultEmail;
import com.shop.validation.group.defaults.DefaultNewEmail;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultEmail.class, DefaultNewEmail.class, UpdatedEmail.class})
public interface UpdateEmailSequence {
}
