package pl.coderslab.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Set;
//
//@Entity
//@Data
//public class User
////        implements UserDetails
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(unique = true)
//    @NotNull
//    private String username;
//    @Column(unique = true)
//    @NotNull
//    private String email;
//    @NotNull
//    private String password;
//    @NotNull
//    private boolean enabled;
//
////    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
////    @OnDelete(action = OnDeleteAction.CASCADE)
////    private Set<Role> authorities;
//
//    public User() {
//    }
//
//    public User(String username, String password, boolean enabled) {
//        this.username = username;
//        this.password = password;
//        this.enabled = enabled;
//    }
//
//    public User(String username, String email, String password, boolean enabled) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.enabled = enabled;
//    }
////
////    @Override
////    public Collection<? extends GrantedAuthority> getAuthorities() {
////        return authorities;
////    }
////
////    @Override
////    public String getPassword() {
////        return password;
////    }
////
////    @Override
////    public String getUsername() {
////        return username;
////    }
//}


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;

    public User() {
    }

    public User(String username, String password, String email, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
    }
}
