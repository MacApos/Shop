package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.validation.cartItem.group.defaults.CreateCartItemDefaults;
import com.shop.validation.category.annotation.CategoryExistsById;
import com.shop.validation.category.annotation.ParentExistsById;
import com.shop.validation.category.annotation.UniqueCategory;
import com.shop.validation.category.annotation.ValidName;
import com.shop.validation.category.group.expensive.CategoryExistsByIdGroup;
import com.shop.validation.category.group.defaults.CreateCategoryDefaults;
import com.shop.validation.category.group.expensive.ParentExistsByIdGroup;
import com.shop.validation.category.group.expensive.UniqueCategoryGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@UniqueCategory(groups = UniqueCategoryGroup.class)
public class Category implements Comparable<Category>, Identifiable<Long> {
    @Id
    @NotNull(groups = CreateCartItemDefaults.class)
    @CategoryExistsById(groups = CategoryExistsByIdGroup.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, groups = CreateCategoryDefaults.class)
    @ValidName(groups = CreateCategoryDefaults.class)
    private String name;

    @Column(unique = true)
    private String path;

    private String breadcrumb;

    @ParentExistsById(groups = ParentExistsByIdGroup.class)
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
