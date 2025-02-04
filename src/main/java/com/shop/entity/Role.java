package com.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @ManyToOne
    @JoinColumn(name="email", referencedColumnName = "email")
    private User user;

    public Role() {
    }

    public Role(RoleEnum name, User user) {
        this.name = name;
        this.user = user;
    }
}