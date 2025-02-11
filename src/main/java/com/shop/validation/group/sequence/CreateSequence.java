package com.shop.validation.group.sequence;

import com.shop.validation.group.Create;
import com.shop.validation.group.defaults.DefaultEmail;
import com.shop.validation.group.defaults.DefaultPassword;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, DefaultPassword.class, DefaultEmail.class, Create.class})
public interface CreateSequence {
}
