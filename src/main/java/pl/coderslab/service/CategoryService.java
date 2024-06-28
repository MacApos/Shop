package pl.coderslab.service;

import pl.coderslab.dto.CategoryDto;
import pl.coderslab.entity.Category;

import java.util.List;

public interface CategoryService {
    String SEARCH_PATH = "search";
    String PRODUCT_PATH = "product";

    List<CategoryDto> getHierarchyMap();

    List<Category> getParents(Category category);

    String normalizeName(String unnormalized);

    String[] splitPathAroundProduct(String path);

    List<Category> findAllByParentCategory(Category category);

    List<Category> findAll();

    Category findById(Long id);

    Category findByName(String name);

    Category findByPath(String path);

    void save(Category category);


}