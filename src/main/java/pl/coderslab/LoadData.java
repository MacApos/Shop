//package pl.coderslab;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import pl.coderslab.entity.Category;
//import pl.coderslab.entity.Product;
//import pl.coderslab.entity.Role;
//import pl.coderslab.entity.User;
//import pl.coderslab.service.CategoryService;
//import pl.coderslab.service.ProductService;
//import pl.coderslab.service.RoleService;
//import pl.coderslab.service.UserService;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Configuration
//public class LoadData {
//    private final Map<String, Category> categoriesMap = new HashMap<>();
//
//    @Bean
//    public CommandLineRunner createCategories(CategoryService categoryService) {
//        List<List<String>> categories = List.of(
//                List.of("Artykuły domowe"),
//                List.of("Ubrania"),
//                List.of("Inne"),
//                List.of("Pozostałe"),
//                List.of("Dywany", "Artykuły domowe"),
//                List.of("Kolorowe dywany", "Dywany"),
//                List.of("Jednokolorowe dywany", "Dywany"),
//                List.of("Inne dywany", "Artykuły domowe"),
//                List.of("Różne dywany", "Inne dywany"),
//                List.of("Czapki", "Ubrania")
//        );
//
//        for (List<String> category : categories) {
//            String name = category.get(0);
//            Category newCategory = new Category(name);
//            if (category.size() > 1) {
//                newCategory.setParent(categoryService.findByName(category.get(1)));
//            }
//
//            try {
//                categoryService.save(newCategory);
//            } catch (Error e) {
//                System.out.println(e.getMessage());
//            }
//            categoriesMap.put(name, newCategory);
//        }
//
//        return args -> {
//        };
//    }
//
//    @Bean
//    public CommandLineRunner createProducts(ProductService productService) {
//        List<List<String>> products = List.of(
//                List.of("Czapka 1", "Czapki"),
//                List.of("Czapka 2", "Czapki"),
//                List.of("Czerwony dywan", "Kolorowe dywany"),
//                List.of("Zielony dywan", "Kolorowe dywany"),
//                List.of("Dywan wzór 1", "Jednokolorowe dywany"),
//                List.of("Dywan wzór 2", "Jednokolorowe dywany"),
//                List.of("Dywan do salonu", "Różne dywany"),
//                List.of("Dywan dla dziecka", "Różne dywany"),
//                List.of("Torba 1", "Inne"),
//                List.of("Torba 2", "Inne")
//        );
//
//        for (List<String> product : products) {
//            Product newProduct = new Product(
//                    product.get(0),
//                    "Lorem ipsum",
//                    10.0,
//                    "crochet-hat.jpg",
//                    categoriesMap.get(product.get(1)));
//
//            try {
//                productService.save(newProduct);
//            } catch (Error e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        return args -> {
//        };
//    }
//
//    @Bean
//    public CommandLineRunner createUsersAndRoles(UserService userService, RoleService roleService) {
//        String roleUser = "ROLE_USER";
//        String roleAdmin = "ROLE_ADMIN";
//        List<List<String>> users = List.of(
//                List.of("admin", "a", "a", roleUser, roleAdmin),
//                List.of("user", "u", "u", roleUser)
//        );
//
//        for (List<String> user : users) {
//            String name = user.get(0);
//            User newUser = new User(name, user.get(1), user.get(2), true);
//            userService.save(newUser);
//            user.subList(3, user.size()).forEach(r -> roleService.save(new Role(r,newUser)));
//        }
//
//        return args -> {
//        };
//    }
//
//}
