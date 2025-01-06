package pl.coderslab.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
        "name", "parent_category_id"}))
@Data
@NoArgsConstructor
public class Category implements Comparable<Category> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String namePath;
    private String hierarchyPath;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Category parent;

    @Transient
    private Set<Category> children = new TreeSet<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private List<Product> products = new ArrayList<>();

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
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(namePath, category.namePath) && Objects.equals(parent, category.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, namePath, parent);
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

    @Override
    public String toString() {
        return "Category{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", namePath='" + namePath + '\'' +
               ", hierarchyPath='" + hierarchyPath + '\'' +
               ", parent=" + parent +
               '}';
    }
}
