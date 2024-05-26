package pl.coderslab.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Category;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.service.CategoryService;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

//    public List<Object> createCategoriesHierarchy(List<Category> parents) {
//        List<Object> list = new ArrayList<>();
//        List<Object> childList = new ArrayList<>();
//        for (Category parent : parents) {
//            List<Category> children = findChildrenByParentCategory(parent);
//
//            if (!children.isEmpty()) {
//                List<Object> newParents = createCategoriesHierarchy(children);
//                List<Object> parentList = new ArrayList<>(List.of(parent.getName()));
//                parentList.addAll(newParents);
//                list.add(parentList);
//            }
//            else {
//                childList.add(parent.getName());
//            }
//        }
//        if(!childList.isEmpty()){
//            list.add(childList);
//        }
//        return list;
//    }

    public HashMap<String, Object> createCategoriesHierarchy(List<Category> parents) {
        HashMap<String, Object> map = new HashMap<>();
        for (Category parent : parents) {
            map.put(parent.getName(), null);
            List<Category> children = findChildrenByParentCategory(parent);
            if (!children.isEmpty()) {
                HashMap<String, Object> newParents = createCategoriesHierarchy(children);
                map.replace(parent.getName(), newParents);
            }
        }
        return map;
    }

    @Override
    public HashMap<String, Object> findAll() {
        List<Category> grandparents = categoryRepository.findChildrenByParentCategoryIsNull();
        return createCategoriesHierarchy(grandparents);
    }

    @Override
    public List<Category> findChildrenByParentCategory(Category category) {
        return categoryRepository.findByParentCategory(category);
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElse(null);
    }
}