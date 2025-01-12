package pl.coderslab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.entity.Role;
import pl.coderslab.entity.User;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;
import pl.coderslab.service.RoleService;
import pl.coderslab.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class LoadData {
    private final Map<String, Category> categoriesMap = new HashMap<>();

    @Bean
    public CommandLineRunner createCategories(CategoryService categoryService) {
        List<List<String>> categories = List.of(
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
        );

        for (List<String> category : categories) {
            String name = category.get(0);
            Category newCategory = new Category(name);
            Category exisitngCategory = categoryService.findByName(name);
            if (exisitngCategory != null) {
                categoriesMap.put(name, exisitngCategory);
                continue;
            }

            if (category.size() > 1) {
                newCategory.setParent(categoryService.findByName(category.get(1)));
            }

            categoryService.save(newCategory);
            categoriesMap.put(name, newCategory);

        }

        return args -> {
        };
    }

    @Bean
    public CommandLineRunner createProducts(ProductService productService) {
        List<List<String>> products = List.of(
                List.of("Czapka 1", "Czapki"),
                List.of("Czapka 2", "Czapki"),
                List.of("Czerwony dywan", "Kolorowe dywany"),
                List.of("Zielony dywan", "Kolorowe dywany"),
                List.of("Dywan wzór 1", "Jednokolorowe dywany"),
                List.of("Dywan wzór 2", "Jednokolorowe dywany"),
                List.of("Dywan do salonu", "Różne dywany"),
                List.of("Dywan dla dziecka", "Różne dywany"),
                List.of("Torba 1", "Inne"),
                List.of("Torba 2", "Inne")
        );

        for (List<String> product : products) {
            Product newProduct = new Product(
                    product.get(0),
                    "Lorem ipsum",
                    10.0,
                    "crochet-hat.jpg",
                    categoriesMap.get(product.get(1)));
            try {
                productService.save(newProduct);
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
        }

        return args -> {
        };
    }

    @Bean
    public CommandLineRunner createUsersAndRoles(UserService userService, RoleService roleService) {
        String roleUser = "ROLE_USER";
        String roleAdmin = "ROLE_ADMIN";
        List<User> users = List.of(
                new User("admin", "Adamin", "Nowak", "admin", "admin@gmail.com"),
                new User("user", "Andrzej", "Userski", "user", "user@gmail.com")
        );
        List<List<String>> roles = List.of(List.of(roleUser, roleAdmin), List.of(roleUser));

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            user.setEnabled(true);
            userService.save(user);
            roles.get(i).forEach(r -> roleService.save(new Role(r, user)));
        }

        return args -> {
        };
    }

}
