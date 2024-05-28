package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentCategory(Category parent);
    
    List<Category> findAllChildrenByParentCategoryIsNull();

    Category findByNameAndParentCategory(String name, Category parent);

    Category findByPath(String path);

    Category findByName(String name);
}