package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.validation.product.group.CreateCartItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "parent_id"}))
@Data
@NoArgsConstructor
public class Category implements Comparable<Category>, Identifiable<Long> {
    @Id
    @NotNull(groups = CreateCartItem.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(unique = true)
    private String path;

//    private String hierarchyPath;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Category parent;

    @Transient
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Category> children;

    @Transient
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private Set<Product> products;

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(path, category.path) && Objects.equals(parent, category.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path, parent);
    }

    private boolean startsWith(String string, String prefix) {
        return string.toLowerCase().startsWith(prefix);
    }

    @Override
    public int compareTo(Category category) {
        String categoryName = category.getName();
        boolean aIsOther = startsWith(name, "inne");
        boolean bIsOther = startsWith(categoryName, "inne");
        if (aIsOther & !bIsOther) {
            return 1;
        } else if (!aIsOther & bIsOther) {
            return -1;
        }
        return name.compareTo(categoryName);
    }
}
