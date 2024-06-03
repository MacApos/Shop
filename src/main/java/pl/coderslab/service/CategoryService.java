package pl.coderslab.service;

import pl.coderslab.entity.Category;

import java.util.LinkedHashMap;
import java.util.List;

public interface CategoryService {
    String SEARCH_PATH = "search";
    String PRODUCT_PATH = "product";

    LinkedHashMap<Category, Object> getHierarchyMap();

    List<Category> getParentsLine(Category category);

    Category findById(Long id);

    Category findByName(String name);

    Category findByPath(String path);

    void save(Category category);

    String normalizeName(String unnormalized);

    String[] splitPathAroundProduct(String path);
}