package com.shop.entity;

import com.shop.validation.user.annotation.TokenExists;
import com.shop.validation.user.annotation.ValidToken;
import com.shop.validation.user.group.defaults.DefaultToken;
import com.shop.validation.user.group.expensive.UserExists;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class RegistrationToken implements Identifiable<Long> {
    private static final int expiration = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3)
    @NotNull(message = "{invalid.token}", groups = DefaultToken.class)
    @Size(min = 3, message = "{invalid.token}", groups = DefaultToken.class)
    @TokenExists(groups = UserExists.class)
    private String token;

    @NotNull
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Future(message = "{expired.token}")
    private LocalDateTime expiryDate;

    @ColumnDefault("true")
    @NotNull
    @AssertTrue(message = "{used.token}", groups = ValidToken.class)
    private boolean active = true;

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

    @Override
    public String toString() {
        return "RegistrationToken{" +
                "active=" + active +
                ", id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user.getId() +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
