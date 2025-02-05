package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.Login;
import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, Login.class})
public interface DefaultAndLogin {
}
