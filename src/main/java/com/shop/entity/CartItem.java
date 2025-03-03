package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.validation.cartItem.annotation.CartItemExists;
import com.shop.validation.cartItem.group.database.CartItemExistsGroup;
import com.shop.validation.cartItem.group.defaults.CreateCartItemDefaults;
import com.shop.validation.cartItem.group.defaults.DeleteCartItemDefaults;
import com.shop.validation.cartItem.group.defaults.UpdateCartItemDefaults;
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
@CartItemExists(groups = CartItemExistsGroup.class)
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @NotNull(groups = DeleteCartItemDefaults.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, groups = {CreateCartItemDefaults.class, UpdateCartItemDefaults.class})
    @ColumnDefault("1")
    private int quantity = 1;

    @NotNull(groups = CreateCartItemDefaults.class)
    @Valid
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @NotNull
    @Valid
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cart cart;

    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
    }
}
