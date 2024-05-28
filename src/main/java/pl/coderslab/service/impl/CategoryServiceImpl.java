package pl.coderslab.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Category;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.service.CategoryService;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Stream;

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

    private List<Category> sortCategories(List<Category> categories) {
        if (categories.size() > 1) {
            return categories.stream().sorted((Comparator.comparing(Category::getName))).toList();
        }
        return categories;
    }

    private final List<String> othersPropositions = List.of("inne", "pozostałe");

    private List<Category> getSortedCategories(List<Category> categories) {
        List<Category> others = new ArrayList<>();
        List<Category> main = new ArrayList<>(categories);
        for (Category category : categories) {
            for (String proposition : othersPropositions) {
                if (category.getName().toLowerCase().contains(proposition)) {
                    main.remove(category);
                    others.add(category);
                }
            }
        }
        main = sortCategories(main);
        others = sortCategories(others);
        return Stream.concat(main.stream(), others.stream()).toList();
    }

    private LinkedHashMap<Category, Object> getCategoriesInHierarchy(List<Category> parents) {
        parents = getSortedCategories(parents);
        LinkedHashMap<Category, Object> map = new LinkedHashMap<>();
        for (Category parent : parents) {
            map.put(parent, null);
            List<Category> children = categoryRepository.findAllByParentCategory(parent);
            if (!children.isEmpty()) {
                LinkedHashMap<Category, Object> parentWithChildren = getCategoriesInHierarchy(children);
                map.replace(parent, parentWithChildren);
            }
        }
        return map;
    }

    @Override
    public LinkedHashMap<Category, Object> getHierarchyMap() {
        List<Category> grandparents = categoryRepository.findAllChildrenByParentCategoryIsNull();
        return getCategoriesInHierarchy(grandparents);
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findByPath(String path) {
        return categoryRepository.findByPath(path);
    }

    private String normalize(String unnormalized) {
        String path = unnormalized.replaceAll(" ", "-").toLowerCase();
        return Normalizer.normalize(path, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[\\u0141-\\u0142]", "l");
    }

    @Override
    public void save(Category category) {
        Category parent = category.getParentCategory();
        String name = category.getName();
        if (categoryRepository.findByNameAndParentCategory(name, parent) != null) {
            throw new Error("Category already exists");
        }
        category.setPath(parent == null ? normalize(name) : parent.getPath() + "/" + normalize(name));

        categoryRepository.save(category);
    }

}