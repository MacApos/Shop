package com.shop;

import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.entity.Role;
import com.shop.entity.User;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import com.shop.service.RoleService;
import com.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final RoleService roleService;

    @EventListener(ApplicationReadyEvent.class)
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
                new Product("Dywan dla dziecka", categoriesMap.get("Różne dywany")),
                new Product("Torba 1", categoriesMap.get("Inne")),
                new Product("Torba 2", categoriesMap.get("Inne"))
        );

        for (Product product : products) {
            if (productService.existsByNameAndCategory(product.getName(), product.getCategory())) {
                continue;
            }
            product.setDescription("Lorem ipsum");
            product.setPrice(10.0);
            product.setImage("crochet-hat.jpg");
            productService.save(product);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createUsers() {
        String roleUser = "ROLE_USER";
        String roleAdmin = "ROLE_ADMIN";
        Map<User, List<String>> users = Map.of(
                new User("admin", "Adamin", "Nowak", "admin", "admin@gmail.com"),
                List.of(roleUser, roleAdmin),
                new User("user", "Andrzej", "Userski", "user", "user@gmail.com"),
                List.of(roleUser)
        );

        for (Map.Entry<User, List<String>> entry : users.entrySet()) {
            User user = entry.getKey();
            if (userService.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
                continue;
            }
            user.setEnabled(true);
            userService.save(user);
            entry.getValue().forEach(r -> roleService.save(new Role(r, user)));
        }

    }
}