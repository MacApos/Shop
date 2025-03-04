package com.shop.service;

import com.shop.mapper.CategoryMapper;
import com.shop.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shop.entity.Category;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryService extends AbstractService<Category> {
    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;
    private final CategoryMapper categoryMapper;

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

    private Set<Category> recursiveFindChildren(List<Category> parents, Long parentId) {
        parents = getSortedCategories(parents);
        Set<Category> set = new TreeSet<>();

        for (Category parent : parents) {
            List<Category> children = findAllByParent(parent);
            if (!children.isEmpty()) {
                parent.setChildren(new TreeSet<>(recursiveFindChildren(children, parent.getId())));
            }
            set.add(parent);
        }
        return set;
    }

    public Set<Category> findAllLastChildren() {
        List<Category> parents = categoryRepository.findAllByParentIsNull();
        Set<Category> lastChildren = new TreeSet<>();

        for (int i = 0; i < parents.size(); i++) {
            Category parent = parents.get(i);
            List<Category> foundChildren = findAllByParent(parent);
            if (!foundChildren.isEmpty()) {
                parents.addAll(foundChildren);
            } else {
                lastChildren.add(parent);
            }
        }
        return lastChildren;
    }

    public Set<Category> getHierarchy() {
        List<Category> categories = new ArrayList<>(categoryRepository.findAllByParentIsNull());
        TreeSet<Category> hierarchy = new TreeSet<>(categories);

        Deque<Category> deque = new ArrayDeque<>(categories);
        while (!deque.isEmpty()) {
            Category currentCategory = deque.pop();
            List<Category> children = findAllByParent(currentCategory);
            if (!children.isEmpty()) {
                currentCategory.setChildren(new TreeSet<>(children));
                deque.addAll(children);
            }
        }
        return hierarchy;
    }

    public Set<Category> getHierarchyV2() {
        List<Category> categories = new ArrayList<>(categoryRepository.findAllByParentIsNull());
        TreeSet<Category> hierarchy = new TreeSet<>(categories);
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            List<Category> children = findAllByParent(category);
            if (!children.isEmpty()) {
                category.setChildren(new TreeSet<>(children));
                categories.addAll(children);
            }
        }
        return hierarchy;
    }

    public Set<Category> getHierarchyV3() {
        List<Category> categories = categoryRepository.findAllByParentIsNull();
        Set<Category> categorySet = new TreeSet<>();
        for (Category grandparent : categories) {
            categorySet.add(findChildren(grandparent));
        }
        return categorySet;
    }

    public Category findChildren(Category category) {
        List<Category> categories = new ArrayList<>(List.of(category));

        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            List<Category> children = findAllByParent(cat);
            if (!children.isEmpty()) {
                cat.setChildren(new TreeSet<>(children));
                categories.addAll(children);
            }
        }
        return category;
    }

    public List<Category> getHierarchyFlat() {
        List<Category> categories = new ArrayList<>(categoryRepository.findAllByParentIsNull());
        Collections.sort(categories);
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            List<Category> children = findAllByParent(cat);
            if (!children.isEmpty()) {
                Collections.sort(children);
                categories.addAll(i + 1, children);
            }
        }
        return categories;
    }

    private TreeSet<Category> filterCategories(List<Category> categories, Category category) {
        return categories
                .stream()
                .filter(c -> !c.equals(category))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<Category> getAlternativeParentsV2(Category category) {
        List<Category> categories = new ArrayList<>(categoryRepository.findAllByParentIsNull());
        Set<Category> alternatives = filterCategories(categories, category);

        boolean found = false;
        for (int i = 0; i < categories.size(); i++) {
            Category currentCategory = categories.get(i);
            List<Category> children = findAllByParent(currentCategory);
            if (!children.isEmpty()) {
                Set<Category> filtered;
                if (!found && children.contains(category)) {
                    found = true;
                    filtered = filterCategories(children, category);
                    if (filtered.isEmpty()) {
                        continue;
                    }
                } else {
                    filtered = new TreeSet<>(children);
                }
                currentCategory.setChildren(filtered);
                categories.addAll(filtered);
            }
        }
        return alternatives;
    }

    public Set<Category> getAlternativeParents(Category category) {
        List<Category> categories = new ArrayList<>(categoryRepository.findAllByParentIsNull());
        Set<Category> alternatives = filterCategories(categories, category);
        Deque<Category> deque = new ArrayDeque<>(categories);

        boolean found = false;
        while (!deque.isEmpty()) {
            Category currentCategory = deque.pop();
            List<Category> children = findAllByParent(currentCategory);
            if (!children.isEmpty()) {
                Set<Category> filtered;
                if (!found && children.contains(category)) {
                    found = true;
                    filtered = filterCategories(children, category);
                    if (filtered.isEmpty()) {
                        continue;
                    }
                } else {
                    filtered = new TreeSet<>(children);
                }

                currentCategory.setChildren(filtered);
                deque.addAll(filtered);
            }
        }

        return alternatives;
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

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public boolean existsByParent(Long id) {
        return categoryRepository.existsByParentId(id) != null;
    }

    public boolean existsByNameAndParent(String name, Category category) {
        return categoryRepository.existsByNameAndParent(name, category);
    }

    public boolean existsByNameAndParentIsNull(String name) {
        return categoryRepository.existsByNameAndParentIsNull(name);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category findByNameAndParent(Category category) {
        return categoryRepository.findByNameAndParent(category.getName(), category.getParent());
    }

    public List<Category> findAllByParent(Category category) {
        return categoryRepository.findAllByParent(category);
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

    public void populateCategory(Category category) {
        String normalizedName = normalizeName(category.getName());
        String hierarchy = String.valueOf(category.getId());
        Category parent = category.getParent();
        if (parent != null) {
            String name = parent.getName();
            if (name == null) {
                parent = findById(parent.getId());
            }
            normalizedName = normalizeName(parent.getName()) + "-" + normalizedName;
            hierarchy = parent.getHierarchy() + "-" + hierarchy;
        }
        normalizedName = normalizedName + "-" + category.getId();
        category.setPath(normalizedName);
        category.setHierarchy(hierarchy);
    }

    public void updateHierarchy(Category category) {
        List<Category> categories = findAllByParent(category);
        Deque<Category> deque = new ArrayDeque<>(categories);
        while (!deque.isEmpty()) {
            Category currentCategory = deque.pop();
            Category parent = currentCategory.getParent();
            List<Category> children = findAllByParent(currentCategory);
            if (!children.isEmpty()) {
                deque.addAll(children);
            }
            currentCategory.setHierarchy(parent.getHierarchy() + "-" + currentCategory.getId());
            entityManager.persist(currentCategory);
        }
    }

    @Transactional
    public void save(Category category) {
        entityManager.persist(category);
        populateCategory(category);
    }

    @Transactional
    public void update(Category category) {
        Category existingCategory = findById(category.getId());
        boolean newParent = !existingCategory.getParent().equals(category.getParent());
        categoryMapper.update(category, existingCategory);
        save(existingCategory);

        if (newParent) {
            updateHierarchy(existingCategory);
        }
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

}