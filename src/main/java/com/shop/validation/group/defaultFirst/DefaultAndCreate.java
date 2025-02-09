package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.Create;
import com.shop.validation.group.DefaultEmail;
import com.shop.validation.group.DefaultPassword;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, DefaultPassword.class, DefaultEmail.class, Create.class})
public interface DefaultAndCreate {
}
