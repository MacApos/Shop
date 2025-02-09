package com.shop.validation.group.defaultFirst;

import com.shop.validation.group.DefaultEmail;
import com.shop.validation.group.DefaultPassword;
import com.shop.validation.group.ResetPassword;
import com.shop.validation.group.UpdatedEmail;
import jakarta.validation.GroupSequence;

@GroupSequence({DefaultEmail.class, UpdatedEmail.class})
public interface DefaultAndUpdateEmail {
}
