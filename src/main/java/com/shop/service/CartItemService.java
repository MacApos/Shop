package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Product;
import com.shop.entity.User;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final EntityManager entityManager;
    private final CartItemRepository cartItemRepository;

    public List<CartItem> findByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    public CartItem findByProductAndCart(Cart cart,Product product) {
        return cartItemRepository.findByCartAndProduct(cart, product);
    }

    @Transactional
    public void save(CartItem cartItem) {
        entityManager.persist(cartItem);
    }

}