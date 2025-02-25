package com.shop.validation.user.group.sequence;

import com.shop.validation.user.annotation.ValidToken;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({ValidToken.class, Default.class})
public interface ValidTokenSequence {
}
