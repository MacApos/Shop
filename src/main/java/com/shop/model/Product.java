package com.shop.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.shop.validation.cartItem.group.defaults.CreateCartItemDefaults;
import com.shop.validation.global.annotation.MinSize;
import com.shop.validation.product.annotation.ProductExists;
import com.shop.validation.product.annotation.UniqueProduct;
import com.shop.validation.product.group.database.ProductExistsGroup;
import com.shop.validation.product.group.database.UniqueProductGroup;
import com.shop.validation.product.group.defaults.CreateProductDefaults;
import com.shop.validation.product.group.defaults.DeleteProductDefaults;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "category_id"}))
@Data
@NoArgsConstructor
@ProductExists(groups = ProductExistsGroup.class)
@UniqueProduct(groups = UniqueProductGroup.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {
    @Id
    @NotNull(groups = {DeleteProductDefaults.class, CreateCartItemDefaults.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @MinSize(groups = CreateProductDefaults.class)
    private String name;

    @NotNull
    @MinSize(min = 10, groups = CreateProductDefaults.class)
    private String description;

    @NotNull
    @DecimalMin(value = "0.1")
    @NotNull(groups = CreateProductDefaults.class)
    @DecimalMin(value = "0.1", groups = CreateProductDefaults.class)
    private Double price;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String image;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String path;

    @NotNull
    @NotNull(groups = {CreateProductDefaults.class, CreateCartItemDefaults.class})
    @Valid
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @JsonIgnore
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

    public Long getCategoryId() {
        return category == null ? null : category.getId();
    }
}
