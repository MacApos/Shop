package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.User;
import com.shop.mapper.CartMapper;
import com.shop.repository.CartRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService extends AbstractService<Cart> {
    private final EntityManager entityManager;
    private final CartRepository cartRepository;

    public Cart findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Transactional
    public void save(Cart cart) {
        entityManager.persist(cart);
    }

}