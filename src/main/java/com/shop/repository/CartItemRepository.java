package com.shop.repository;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);

    CartItem findByProductAndCart(Product product, Cart cart);

    CartItem findByIdAndCart(Long id, Cart cart);

    @Query(value = """
            select ci.*
            from cart_item ci
                    inner join cart c on ci.cart_id = c.id
                    inner join user u on c.user_id = u.id
            where ci.id = :id and u.email = :email;
            """, nativeQuery = true)
    CartItem findByIdAndEmail(@Param("id") Long id, @Param("email") String email);

    @Query(value = """
            select 1
            from cart_item ci
                     inner join cart c on ci.cart_id = c.id
                     inner join user u on c.user_id = u.id
            where ci.id = :id and u.email = :email;
            """, nativeQuery = true)
    Long existsByIdAndEmail(@Param("id") Long id, @Param("email") String email);
}