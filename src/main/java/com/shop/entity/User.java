package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.validation.annotation.*;
import com.shop.validation.group.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Data
//@UniqueEntity(groups = {CheckFirst.class}, service = UserService.class, fields = {"username", "email"})
@ConfirmPassword
@UserAlreadyExist(groups = AlreadyExists.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(min = 3)
    private String username;

    @NotNull
    @Size(min = 3)
    private String firstname;

    @NotNull
    @Size(min = 3)
    private String lastname;

    @NotNullSize
    @NotNull(groups = Login.class)

    @ValidPassword
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private String passwordConfirm;


    //    custom validation
    @Column(unique = true)
    @NotNullEmail
    @NotNullEmail(groups = {DefaultFirst.class})
    @UserExists(groups = {Exists.class})
    private String email;

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

}
