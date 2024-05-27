package pl.coderslab.service.impl;

import org.antlr.v4.runtime.tree.Tree;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.Category;
import pl.coderslab.entity.CategoryNameComparator;
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
            List<Category> children = categoryRepository.findByParentCategory(parent);
            if (!children.isEmpty()) {
                LinkedHashMap<Category, Object> newParents = getCategoriesInHierarchy(children);
                map.replace(parent, newParents);
            }
        }
        return map;
    }

    @Override
    public LinkedHashMap<Category, Object> findAll() {
        List<Category> grandparents = categoryRepository.findChildrenByParentCategoryIsNull();
        return getCategoriesInHierarchy(grandparents);
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public Category findByNameLike(String name) {
        return categoryRepository.findByNameLike(name + "%");
    }

    private String normalized(String unnormalized) {
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
        category.setPath(parent == null ? normalized(name) : parent.getPath() + "/" + normalized(name));

        categoryRepository.save(category);
    }

}