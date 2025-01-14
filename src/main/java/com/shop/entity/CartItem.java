//package pl.coderslab.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(uniqueConstraints=@UniqueConstraint(columnNames={"cart_id", "product_id"}))
//public class CartItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private int quantity = 0;
//    @ManyToOne
//    private Product product;
//    @ManyToOne
//    private Cart cart;
//
//    public CartItem() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//}
