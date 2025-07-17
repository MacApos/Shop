package com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.shop.entity.Category;
import com.shop.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

    Product findByNameAndCategory(String name, Category category);

    Product findByPathAndCategory(String path, Category category);

    List<Product> findAllByCategory(Category category);

    String countQuery = """
            with recursive CategoryTree as (select  id, parent_id from category
                                           where id = :id
                                           union all
                                           select c.id, c.parent_id
                                           from category c
                                                    inner join CategoryTree ct on ct.id = c.parent_id)
            select count(*)
            from product p
                    inner join CategoryTree ct on ct.id = p.category_id
            """;

    @Query(value = """
                with recursive CategoryTree as (select  id, parent_id from category
                                                           where id = :id
                                                           union all
                                                           select c.id, c.parent_id
                                                           from category c
                                                                    inner join CategoryTree ct on ct.id = c.parent_id)
                            select count(*)
                            from product p
                                    inner join CategoryTree ct on ct.id = p.category_id
                """, nativeQuery = true, countQuery = countQuery)
    Page<Product> findAllByCategory(Pageable pageable, @Param("id") int categoryId);

    @NativeQuery(value = countQuery)
    long countAllByCategory(@Param("id") int categoryId);


    boolean existsByNameAndCategory(String name, Category category);

    @Query(value = "select 1 from product where category_id = :categoryId;",
            nativeQuery = true)
    Long existsByCategoryId(@Param("categoryId") Long categoryId);
}