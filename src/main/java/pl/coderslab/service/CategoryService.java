package pl.coderslab.service;

import pl.coderslab.entity.Category;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    HashMap<String, Object> findAll();

    List<Category> findChildrenByParentCategory(Category category);

    Category findById(Long id);
}