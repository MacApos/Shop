package com.shop.service;

import com.shop.model.Cart;
import com.shop.model.CartItem;
import com.shop.model.Product;
import com.shop.model.User;
import com.shop.repository.CartItemRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService extends AbstractService<CartItem> {
    private final EntityManager entityManager;
    private final CartItemRepository cartItemRepository;

    public List<CartItem> findByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    public CartItem findByIdAndUser(Long id, User user) {
        return cartItemRepository.findByIdAndEmail(id, user.getEmail());
    }

    public CartItem findByIdAndCart(Long id, Cart cart) {
        return cartItemRepository.findByIdAndCart(id, cart);
    }

    public CartItem findByProductAndCart(Product product, Cart cart) {
        return cartItemRepository.findByProductAndCart(product, cart);
    }

    public boolean existsByIdAndUser(Long id, User user) {
        return cartItemRepository.existsByIdAndEmail(id, user.getEmail()) != null;
    }

    @Transactional
    public void save(CartItem cartItem) {
        entityManager.persist(cartItem);
    }

    @Transactional
    public void delete(CartItem cartItem) {
        entityManager.remove(cartItem);
    }

}