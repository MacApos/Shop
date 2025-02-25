package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.validation.product.group.CreateCartItem;
import com.shop.validation.product.group.UpdateCartItem;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1)
    @Min(value = 1, groups = {CreateCartItem.class, UpdateCartItem.class})
    @ColumnDefault("1")
    private int quantity = 1;

    @NotNull
    @NotNull(groups = CreateCartItem.class)
    @Valid
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Cart cart;

    public CartItem(int quantity) {
        this.quantity = quantity;
    }

    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }
//
//    public CartItem(int quantity, Product product, Cart cart) {
//        this.quantity = quantity;
//        this.product = product;
//        this.cart = cart;
//    }

    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
    }
}
