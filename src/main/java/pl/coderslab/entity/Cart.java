//package pl.coderslab.entity;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class Cart {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @OneToMany(mappedBy = "cart")
//    private List<CartItem> cartItems;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//}
