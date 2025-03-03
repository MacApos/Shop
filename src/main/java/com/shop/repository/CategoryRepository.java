package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.shop.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParent(Category category);

    List<Category> findAllByParentIsNull();

    Category findByName(String name);

    Category findByNameAndParent(String name, Category category);

    boolean existsByNameAndParentIsNull(String name);

    boolean existsByNameAndParent(String name, Category category);

    @Query(value = "select 1 from category where parent_id = :parentId;", nativeQuery = true)
    Long existsByParentId(@Param("parentId") Long parentId);

    @Query(value = "select 1 from category where name like :name and parent_id = :parentId;", nativeQuery = true)
    Long existsByNameAndParentId(@Param("name") String name, @Param("parentId") Long parentId);
}