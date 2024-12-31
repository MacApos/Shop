package pl.coderslab.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "parent_category_id"}))
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private String parentsPath;

    @JsonBackReference
    @ManyToOne
    private Category parentCategory;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "category_id") // Foreign key in Item table
//    @OrderColumn(name = "category_order") // Maintains the LinkedList order
//    private LinkedList<CategoryOrder> categoryOrder= new LinkedList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(path, category.path) && Objects.equals(parentCategory, category.parentCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path, parentCategory);
    }
}
