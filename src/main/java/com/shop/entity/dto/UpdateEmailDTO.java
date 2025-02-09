package com.shop.entity.dto;

import com.shop.entity.User;
import com.shop.validation.annotation.EmailTaken;
import com.shop.validation.annotation.NotNullEmail;
import com.shop.validation.group.DefaultEmail;
import com.shop.validation.group.UpdatedEmail;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateEmailDTO extends User {
    @NotNullEmail(groups = DefaultEmail.class)
    @EmailTaken(groups = UpdatedEmail.class)
    private String newEmail;
}
