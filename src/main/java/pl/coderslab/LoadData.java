package pl.coderslab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.Product;
import pl.coderslab.service.CategoryService;
import pl.coderslab.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class LoadData {
    Map<String, Category> parentCategoryCache = new HashMap<>();

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
            Category parent;
            if (category.size() > 1) {
                parent = categoryService.findByName(category.get(1));
            } else {
                parent = null;
            }
            Category newCategory = new Category(name, parent);
            parentCategoryCache.put(name, newCategory);

            try {
                categoryService.save(newCategory);
            } catch (Error e) {
               System.out.println(e.getMessage());
            }
            parentCategoryCache.put(name, categoryService.findByName(name));
        }

        return args -> {
        };
    }

    @Bean
    public CommandLineRunner createProducts(CategoryService categoryService, ProductService productService) {
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
            String parentName = product.get(1);
            Product newProduct = new Product(
                    product.get(0),
                    "Lorem ipsum",
                    10.0,
                    "crochet-hat.jpg",
                    parentCategoryCache.get(parentName));

            try {
                productService.save(newProduct);
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
        }
        return args -> {
        };
    }

}
