package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.shop.validation.user.annotation.*;
import com.shop.validation.user.annotation.*;
//import com.shop.validation.user.annotation.UserExists;
import com.shop.validation.user.group.defaults.DefaultPassword;
import com.shop.validation.user.group.defaults.DefaultUpdateUser;
import com.shop.validation.user.group.defaults.DefaultEmail;
import com.shop.validation.user.group.defaults.DefaultNewEmail;
import com.shop.validation.user.group.expensive.*;
import com.shop.validation.user.group.expensive.UserExistsByEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ConfirmPassword(groups = ResetPassword.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(groups = DefaultUpdateUser.class)
    @Size(min = 3, groups = DefaultUpdateUser.class)
    @UniqueUsername(groups = {CreateUser.class, UpdateUser.class})
    private String username;

    @NotNull(groups = DefaultUpdateUser.class)
    @Size(min = 3, groups = DefaultUpdateUser.class)
    private String firstname;

    @NotNull(groups = DefaultUpdateUser.class)
    @Size(min = 3, groups = DefaultUpdateUser.class)
    private String lastname;

    @NotNull(groups = DefaultPassword.class)
    @ValidPassword(groups = ResetPassword.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirm;

    @Column(unique = true)
    @NotNullEmail(groups = DefaultEmail.class)
    @UniqueEmail(groups = CreateUser.class)
    @UserExists(groups = UserExistsByEmail.class)
    private String email;

    @NotNullEmail(groups = DefaultNewEmail.class)
    @UniqueEmail(groups = UpdateEmail.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newEmail;

    @ColumnDefault("false")
    @JsonIgnore
    private boolean enabled = false;

    @Transient
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Role> roles;

    @Transient
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private RegistrationToken registrationToken;

    @Transient
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private Cart cart;

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
