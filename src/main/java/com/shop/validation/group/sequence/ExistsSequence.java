package com.shop.validation.group.sequence;

import com.shop.validation.group.defaults.DefaultEmail;
import com.shop.validation.group.defaults.DefaultToken;
import com.shop.validation.group.Exists;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultEmail.class, DefaultToken.class, Exists.class})
public interface ExistsSequence {
}
