package pl.coderslab.entity;

//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//@Entity
//@Table(uniqueConstraints = @UniqueConstraint(
//        columnNames = {"name", "user_id"}))
//@Data
//public class Role
////        implements GrantedAuthority
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @NotNull
//    private String name;
//
//    @ManyToOne
//    private User user;
//
//    public Role() {
//
//    }
//
//    public Role(String name, User user) {
//        this.name = name;
//        this.user = user;
//    }
//
////    @Override
////    public String getAuthority() {
////        return name;
////    }
//
//}

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Table(name = "authorities")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private User user;

    public Role() {
    }

    public Role(String name, User user) {
        this.name = name;
        this.user = user;
    }
}