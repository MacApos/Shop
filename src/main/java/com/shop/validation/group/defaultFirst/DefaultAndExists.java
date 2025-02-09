package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.DefaultEmail;
import com.shop.validation.group.DefaultToken;
import com.shop.validation.group.Exists;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultEmail.class, DefaultToken.class, Exists.class})
public interface DefaultAndExists {
}
