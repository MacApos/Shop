package com.shop.repository;

import com.shop.entity.Cart;
import com.shop.entity.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);

    @Query(value = "select * from cart where user_id = (select id from user where email like :email);", nativeQuery = true)
    Cart findByEmail(@Param("email") String email);
}