package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @ColumnDefault("1")
    private int quantity = 1;

    @NotNull
    @ManyToOne
    private Product product;

//    @NotNull
    @ManyToOne
    @JsonBackReference
    private Cart cart;


    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public CartItem(int quantity, Product product, Cart cart) {
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }

    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
    }

    @Override
    public String toString() {
        return "CartItem{" +
               "id=" + id +
               ", quantity=" + quantity +
               ", product=" + product +
               ", cart=" + cart.getId() +
               '}';
    }
}
