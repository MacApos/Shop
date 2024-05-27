package pl.coderslab.service;

import pl.coderslab.entity.Category;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

public interface CategoryService {
    LinkedHashMap<Category, Object> findAll();

    Category findById(Long id);

    Category findByNameLike(String name);

    void save(Category category);
}