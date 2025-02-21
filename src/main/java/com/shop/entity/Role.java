package com.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Role() {
    }

    public Role(User user) {
        this.user = user;
    }

    public Role(RoleEnum name, User user) {
        this.name = name;
        this.user = user;
    }
}