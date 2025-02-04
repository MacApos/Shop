package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.service.UserService;
import com.shop.validation.annotations.UserExists;
import com.shop.validation.annotations.UserAlreadyEnabled;
import com.shop.validation.groups.AlreadyEnabled;
import com.shop.validation.groups.CheckFirst;
import com.shop.validation.annotations.UniqueEntity;
import com.shop.validation.groups.Exists;
import com.shop.validation.groups.Login;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.boot.model.internal.XMLContext.*;


import java.util.List;

@Entity
@Data
@UniqueEntity(groups = {CheckFirst.class}, service = UserService.class, fields = {"username", "email"})
@UserExists(groups = {Exists.class})
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

    //    custom validation
    @NotNull
    @NotNull(groups = Login.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //    custom validation
    @Column(unique = true)
    @NotNull
    @NotNull(groups = Login.class)
    @Size(min = 3)
    @Email
    private String email;

//    @AssertFalse(groups = {AlreadyEnabled.class}, message = "{user.already.enabled}")
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
