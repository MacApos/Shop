package pl.coderslab.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.dto.CategoryDto;
import pl.coderslab.entity.Category;
import pl.coderslab.repository.CategoryRepository;
import pl.coderslab.util.CustomMapper;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CustomMapper customMapper;
    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;

    private List<Category> sortCategories(List<Category> categories) {
        if (categories.size() > 1) {
            TreeMap<String, Category> treeMap = new TreeMap<>();
            for (Category category : categories) {
                treeMap.put(category.getName(), category);
            }
            return treeMap.values().stream().toList();
//            return categories.stream().sorted((Comparator.comparing(Category::getName))).toList();
        }
        return categories;
    }

    private final List<String> others = List.of("inne", "pozostałe");

    private List<Category> getSortedCategories(List<Category> categories) {
        List<Category> others = new ArrayList<>();
        List<Category> main = new ArrayList<>();
        for (Category category : categories) {
//            if (this.others.stream().anyMatch(prop -> category.getName().toLowerCase().startsWith(prop))) {
            if (category.getName().toLowerCase().startsWith("inne")) {
                others.add(category);
                continue;
            }
            main.add(category);
        }
        main = sortCategories(main);
        others = sortCategories(others);

        return Stream.concat(main.stream(), others.stream()).toList();
    }

    private List<CategoryDto> recursiveFindChildren(List<Category> parents, Long parentId) {
        parents = getSortedCategories(parents);
        List<CategoryDto> list = new ArrayList<>();

        for (Category parent : parents) {
            CategoryDto parentDto = new CategoryDto();
            customMapper.mapCategoryToDto(parent, parentDto);

            if (parentId != null) {
                parentDto.setParentId(parentId);
            }

            List<Category> children = categoryRepository.findAllChildrenByParent(parent);
            if (!children.isEmpty()) {
                parentDto.setChildren(recursiveFindChildren(children, parent.getId()));
            }
            list.add(parentDto);
        }
        return list;
    }

    private Category findChildren(Category category) {
        List<Category> categories = new ArrayList<>(List.of(category));

        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            List<Category> foundChildren = categoryRepository.findAllChildrenByParent(cat);
            if (!foundChildren.isEmpty()) {
                categories.addAll(foundChildren);
                cat.setChildren(new TreeSet<>(foundChildren));
            }
        }
        return category;
    }

    private Set<Category> findAllLastChildren() {
        List<Category> parents = categoryRepository.findAllByParentIsNull();
        Set<Category> lastChildren = new TreeSet<>();

        for (int i = 0; i < parents.size(); i++) {
            Category parent = parents.get(i);
            List<Category> foundChildren = categoryRepository.findAllChildrenByParent(parent);
            if (!foundChildren.isEmpty()) {
                parents.addAll(foundChildren);
            } else {
                lastChildren.add(parent);
            }
        }
        return lastChildren;
    }

    public Set<Category> getHierarchy() {
        List<Category> grandparents = categoryRepository.findAllByParentIsNull();
        Set<Category> categories = new TreeSet<>();
        for (Category grandparent : grandparents) {
            categories.add(findChildren(grandparent));
        }
        return categories;
    }

    public List<Category> findParents(Category category) {
        List<Category> categories = new ArrayList<>(List.of(category));

        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(0);
            Category parent = cat.getParent();
            if (parent != null) {
                categories.add(0, findById(parent.getId()));
            }
        }
        return categories;
    }

    public List<Category> findParents2(Category category) {
        List<Category> parents = new ArrayList<>(List.of(category));
        Category parent = category.getParent();
        while (parent != null) {
            parent = findById(parent.getId());
            if (parent == null) {
                break;
            }
            parents.add(0, parent);
            parent = parent.getParent();
        }
        return parents;
    }


    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }


    public String[] splitPathAroundProduct(String path) {
        path = path.replace("/search/", "");
        return path.split("/product/");
    }

    public List<Category> findAllByParentCategory(Category category) {
        return categoryRepository.findAllChildrenByParent(category);
    }

//    public List<Category> findAllByParentCategoryId(Long id) {
//        return categoryRepository.findAllByParentCategoryId(String.valueOf(id));
//    }


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findByPath(String path) {
        return categoryRepository.findByNamePath(path);
    }

    public String normalizeName(String unnormalized) {
        if (unnormalized == null) {
            return null;
        }
        String path = unnormalized.replaceAll(" ", "-").toLowerCase();
        return Normalizer.normalize(path, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[\\u0141-\\u0142]", "l");
    }


    @Transactional
    public void save(Category category) {
        Category parent = category.getParent();
        String name = category.getName();

        if (parent != null) {
            categoryRepository.findById(parent.getId())
                    .orElseThrow(() -> new Error("Parent category doesn't exist"));
        }

        Category byNameAndParentCategory = categoryRepository.findByNameAndParent(name, parent);
        if (byNameAndParentCategory != null) {
            throw new Error("Category already exists");
        }

        String normalizedName = normalizeName(name);
        category.setNamePath(parent == null ? normalizedName : parent.getNamePath() + "/" + normalizedName);

        entityManager.persist(category);
//        if (parent != null) {
//            String parentPath;
//            if (parent.getHierarchyPath() == null) {
//                parentPath = String.valueOf(parent.getId());
//            } else {
//                parentPath = parent.getHierarchyPath();
//            }
//            category.setHierarchyPath(parentPath + "-" + category.getId());
//        } else {
//            category.setHierarchyPath(String.valueOf(category.getId()));
//        }

        if (parent == null) {
            category.setHierarchyPath(String.valueOf(category.getId()));
        } else {
            category.setHierarchyPath(parent.getHierarchyPath() + "-" + category.getId());
        }
    }


}