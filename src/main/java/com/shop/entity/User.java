package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.service.UserService;
import com.shop.validator.groups.NewEntity;
import com.shop.validator.UniqueEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.boot.model.internal.XMLContext.*;


import java.util.List;

@Entity
@Data
@UniqueEntity(groups = {NewEntity.class}, service = UserService.class, fields = {"username", "email"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
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

    //    custom validator
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //    custom validator
    @Column(unique = true)
    @NotNull
    @Size(min = 3)
    @Email
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
