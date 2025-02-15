package com.shop.validation.group.sequence;

import com.shop.validation.annotation.ValidToken;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({ValidToken.class, Default.class})
public interface ValidTokenSequence {
}
