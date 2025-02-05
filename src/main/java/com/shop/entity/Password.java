package com.shop.entity;

import com.shop.validation.annotation.ConfirmPassword;
import com.shop.validation.annotation.ValidPassword;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@ConfirmPassword
public class Password {
    @ValidPassword
    private String password;
    private String passwordConfirm;
    private String language;

    public Password(String password, String passwordConfirm, String language) {
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.language = language;
    }
}
