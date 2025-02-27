package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.validation.cartItem.group.defaults.CreateCartItemDefaults;
import com.shop.validation.category.annotation.*;
import com.shop.validation.category.group.database.ParentHasChildrenGroup;
import com.shop.validation.category.group.defaults.DeleteCategoryDefaults;
import com.shop.validation.category.group.database.CategoryExistsByIdGroup;
import com.shop.validation.category.group.defaults.CreateCategoryDefaults;
import com.shop.validation.category.group.database.ParentExistsByIdGroup;
import com.shop.validation.category.group.database.UniqueCategoryGroup;
import com.shop.validation.category.group.defaults.ValidNameGroup;
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
@ParentExistsById(groups = ParentExistsByIdGroup.class)
public class Category implements Comparable<Category>{
    @Id
    @NotNull(groups = {DeleteCategoryDefaults.class, CreateCartItemDefaults.class})
    @CategoryExistsById(groups = CategoryExistsByIdGroup.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(groups = CreateCategoryDefaults.class)
    @Size(min = 3, groups = CreateCategoryDefaults.class)
    @ValidName(groups = ValidNameGroup.class)
    private String name;

    private String path;

    private String breadcrumb;

    @ParentHasChildren(groups = ParentHasChildrenGroup.class)
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
