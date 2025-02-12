package com.shop.entity;

import com.shop.validation.annotation.TokenExists;
import com.shop.validation.group.defaults.DefaultToken;
import com.shop.validation.group.Exists;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class RegistrationToken implements Identifiable<Long> {
    private static final int expiration = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{invalid.token}", groups = DefaultToken.class)
    @Size(min = 3, message = "{invalid.token}", groups = DefaultToken.class)
    @TokenExists(groups = Exists.class)
    private String token;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Future(message = "{expired.token}"
//            , groups = DefaultToken.class
    )
    private LocalDateTime expiryDate;

    @ColumnDefault("true")
    @NotNull
    @AssertTrue
//            (groups = DefaultToken.class)
    private boolean available = true;

    public RegistrationToken() {
    }

    public RegistrationToken(User user) {
        this.user = user;
    }

    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }

    public void setExpiryDate() {
        this.expiryDate = LocalDateTime.now().plusHours(expiration);
    }
}
