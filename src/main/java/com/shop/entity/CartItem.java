package com.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @NotNull
    @Min(1)
    @ColumnDefault("1")
    private int quantity = 1;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @ManyToOne
    private Cart cart;

    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
    }
}
