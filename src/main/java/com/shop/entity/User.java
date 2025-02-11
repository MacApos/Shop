package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.validation.annotation.*;
import com.shop.validation.group.*;
import com.shop.validation.group.defaults.DefaultEmail;
import com.shop.validation.group.defaults.DefaultNewEmail;
import com.shop.validation.group.defaults.DefaultPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data
@ConfirmPassword(groups = {Create.class, ResetPassword.class})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(min = 3)
    @UsernameTaken(groups = {Create.class, UpdateUser.class})
    private String username;

    @NotNull
    @Size(min = 3)
    private String firstname;

    @NotNull
    @Size(min = 3)
    private String lastname;

    @NotNull(groups = {DefaultPassword.class, Login.class})
    @ValidPassword(groups = {Create.class, ResetPassword.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    @NotNull(groups = DefaultPassword.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirm;

    @Column(unique = true)
    @NotNullEmail(groups = DefaultEmail.class)
    @EmailTaken(groups = Create.class)
    @UserExists(groups = {Exists.class, UpdatedEmail.class})
    private String email;

    @NotNullEmail(groups = DefaultNewEmail.class)
    @EmailTaken(groups = UpdatedEmail.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newEmail;

    @ColumnDefault("false")
    @JsonIgnore
    private boolean enabled = false;

    @Transient
    @JsonIgnore
    private List<Role> roles;

    public User() {
    }

    public User(String username, String firstname, String lastname, String password, String email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
    }

    public User(String username, String firstname, String lastname, String password, String passwordConfirm, String email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }
}
