package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.validation.annotation.*;
import com.shop.validation.group.*;
import com.shop.validation.group.defaults.DefaultUpdateUser;
import com.shop.validation.group.defaults.DefaultEmail;
import com.shop.validation.group.defaults.DefaultNewEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data
@ConfirmPassword(groups = ResetPassword.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(groups = DefaultUpdateUser.class)
    @Size(min = 3, groups = DefaultUpdateUser.class)
    @UsernameTaken(groups = {Create.class, Update.class})
    private String username;

    @NotNull(groups = DefaultUpdateUser.class)
    @Size(min = 3, groups = DefaultUpdateUser.class)
    private String firstname;

    @NotNull(groups = DefaultUpdateUser.class)
    @Size(min = 3, groups = DefaultUpdateUser.class)
    private String lastname;

    @NotNull(groups = Login.class)
    @ValidPassword(groups = ResetPassword.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirm;

    @Column(unique = true)
    @NotNullEmail(groups = DefaultEmail.class)
    @EmailTaken(groups = Create.class)
    @UserExists(groups = Exists.class)
    private String email;

    @NotNullEmail(groups = DefaultNewEmail.class)
    @EmailTaken(groups = UpdateEmail.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newEmail;

    @ColumnDefault("false")
    @JsonIgnore
    private boolean enabled = false;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RegistrationToken registrationToken;

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
