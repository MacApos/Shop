package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.validation.cartItem.group.defaults.CreateCartItemDefaults;
import com.shop.validation.product.annotation.ProductExistsById;
import com.shop.validation.product.group.ProductExistsByIdGroup;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "category_id"}))
@Data
@NoArgsConstructor
public class Product implements Identifiable<Long> {
    @Id
    @NotNull(groups = CreateCartItemDefaults.class)
    @ProductExistsById(groups = ProductExistsByIdGroup.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @DecimalMin(value = "0.1")
    @ColumnDefault("0.1")
    private Double price = 0.1;

    private String image;

    private String path;

    @NotNull
    @NotNull(groups = CreateCartItemDefaults.class)
    @Valid
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Transient
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private Set<CartItem> cartItem;

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Product(String name, String description, Double price, String image, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public Product(String name, String description, Double price, String image, String path, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.path = path;
        this.category = category;
    }
}
