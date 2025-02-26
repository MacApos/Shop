package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Product;
import com.shop.entity.User;
import com.shop.repository.CartItemRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService extends AbstractService<CartItem> implements ServiceInterface<CartItem> {
    private final EntityManager entityManager;
    private final CartItemRepository cartItemRepository;

    public List<CartItem> findByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    public CartItem findByIdAndUser(Long id, User user) {
        return cartItemRepository.findByIdAndEmail(id, user.getEmail());
    }

    public CartItem findByIdAndCart(Long id,Cart cart) {
        return cartItemRepository.findByIdAndCart(id, cart);
    }

    public CartItem findByProductAndCart(Product product, Cart cart) {
        return cartItemRepository.findByProductAndCart(product, cart);
    }

    public boolean existsByIdAndUser(Long id, User user) {
        return cartItemRepository.existsByIdAndEmail(id, user.getEmail());
    }

    @Transactional
    public void save(CartItem cartItem) {
        entityManager.persist(cartItem);
    }

    @Transactional
    public void delete(CartItem cartItem){
        entityManager.remove(cartItem);
    }

}