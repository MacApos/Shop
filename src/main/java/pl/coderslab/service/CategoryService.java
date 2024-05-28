package pl.coderslab.service;

import pl.coderslab.entity.Category;

import java.util.LinkedHashMap;

public interface CategoryService {
    LinkedHashMap<Category, Object> getHierarchyMap();

    Category findById(Long id);

    Category findByName(String name);

    Category findByPath(String path);

    void save(Category category);
}