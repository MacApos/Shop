package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.User;
import com.shop.repository.CartRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService extends AbstractService<CartItem> {
    private final EntityManager entityManager;
    private final CartRepository cartRepository;

    public Cart findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public Cart findByEmail(String email) {
        return cartRepository.findByEmail(email);
    }

    public Cart findOrCreate(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart(user);
        }
        return cart;
    }

    @Transactional
    public void save(Cart cart) {
        entityManager.persist(cart);
    }

}