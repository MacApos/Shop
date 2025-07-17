package com.shop.listener;

import com.shop.entity.*;
import com.shop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.shop.entity.RoleEnum.*;

@Component
@RequiredArgsConstructor
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final RoleService roleService;
    private final RegistrationTokenService registrationTokenService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createCategories();
        createUsers();
        createCart();
    }

    public void createCategories() {
        Map<String, Category> categoriesMap = new HashMap<>();
        ArrayList<List<String>> categories = new ArrayList<>(List.of(
                List.of("Artykuły domowe"),
                List.of("Ubrania"),
                List.of("Inne"),
                List.of("Pozostałe"),
                List.of("Dywany", "Artykuły domowe"),
                List.of("Kolorowe dywany", "Dywany"),
                List.of("Jednokolorowe dywany", "Dywany"),
                List.of("Inne dywany", "Artykuły domowe"),
                List.of("Różne dywany", "Inne dywany"),
                List.of("Czapki", "Ubrania")
        ));

        categories = categories
                .stream()
                .sorted(Comparator.comparingInt(List::size))
                .collect(Collectors.toCollection(ArrayList::new));

        for (List<String> category : categories) {
            String name = category.get(0);
            Category newCategory = new Category(name);
            if (category.size() > 1) {
                newCategory.setParent(categoryService.findByName(category.get(1)));
            }
            Category existingCategory = categoryService.findByNameAndParent(newCategory);
            if (existingCategory == null) {
                categoryService.save(newCategory);
                categoriesMap.put(name, newCategory);
            } else {
                categoriesMap.put(name, existingCategory);
            }
        }
        createProducts(categoriesMap);
    }

    public void createProducts(Map<String, Category> categoriesMap) {
        List<Product> products = List.of(
                new Product("Czapka 1", categoriesMap.get("Czapki")),
                new Product("Czapka 2", categoriesMap.get("Czapki")),
                new Product("Czerwony dywan", categoriesMap.get("Kolorowe dywany")),
                new Product("Zielony dywan", categoriesMap.get("Kolorowe dywany")),
                new Product("Dywan wzór 1", categoriesMap.get("Jednokolorowe dywany")),
                new Product("Dywan wzór 2", categoriesMap.get("Jednokolorowe dywany")),
                new Product("Dywan do salonu", categoriesMap.get("Różne dywany")),
                new Product("Dywan do łazienki", categoriesMap.get("Różne dywany")),
                new Product("Torba 1", categoriesMap.get("Inne")),
                new Product("Torba 2", categoriesMap.get("Inne"))
        );

        double price = 0.99;
        for (Product product : products) {
            if (productService.existsByNameAndCategory(product.getName(), product.getCategory())) {
                continue;
            }
            price += 1;
            product.setDescription("Lorem ipsum");
            product.setPrice(price);
            product.setImage("crochet-hat.jpg");
            productService.save(product);
        }
    }

    public void createUsers() {
        Map<User, List<RoleEnum>> users = Map.of(
                new User("admin", "Adamin", "Nowak", "admin", "admin@gmail.com"),
                List.of(ROLE_USER, ROLE_ADMIN),
                new User("user", "Andrzej", "Userski", "user", "user@gmail.com"),
                List.of(ROLE_USER)
        );

        for (Map.Entry<User, List<RoleEnum>> entry : users.entrySet()) {
            User user = entry.getKey();

            if (userService.existsByUsernameOrEmail(user)) {
                continue;
            }
            user.setEnabled(true);
            userService.save(user);
            entry.getValue().forEach(r -> roleService.save(new Role(r, user)));
        }

        RegistrationToken token = new RegistrationToken(userService.findByUsername("admin"));
        token.setToken("test");
        token.setExpiryDate(LocalDateTime.now().plusSeconds(1200));
        registrationTokenService.save(token);
    }

    public void createCart() {
        User user = userService.findByUsername("user");
        Cart cart = new Cart(user);
        cartService.save(cart);

        List<CartItem> cartItems = List.of(
                new CartItem(1, productService.findByName("Czapka 1")),
                new CartItem(1, productService.findByName("Czapka 2")),
                new CartItem(1, productService.findByName("Czerwony dywan"))
        );

        for (CartItem cartItem : cartItems) {
            cartItem.setCart(cart);
            cartItemService.save(cartItem);
        }
    }
}
