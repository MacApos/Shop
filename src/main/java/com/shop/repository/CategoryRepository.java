package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.shop.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllChildrenByParent(Category category);

    List<Category> findAllByParentIsNull();

    Category findByName(String name);

    Category findByNameAndParent(String name, Category category);

    boolean existsByName(String name);

    boolean existsByNameAndParentIsNull(String name);

    boolean existsByNameAndParent(String name, Category parent);

    @Query(value = "select 1 from category where name like 'Category3' and parent_id = 1;",
            nativeQuery = true)
    Long existsByNameAndParentId(@Param("name") String name, @Param("parentId") Long parentId);
}