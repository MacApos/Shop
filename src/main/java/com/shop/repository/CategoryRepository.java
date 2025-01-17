package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.shop.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllChildrenByParent(Category category);

    List<Category> findAllByParentIsNull();

    Category findByNameAndParent(String name, Category category);

//    Test in CategoryServiceTest
    Category findByNamePath(String path);

    Category findByName(String name);

    List<Category> findByParent(Category parent);

    @Query(value = "select * from category", nativeQuery = true)
    List<Category> findAllSQL();

    @Query(value = "select * from category where hierarchy_path regexp concat('(?:^|-)',?1,'(?:-|$)');", nativeQuery = true)
    List<Category> findAllByParentId(Long id);

    boolean existsByNameAndParent(String name, Category parent);
}