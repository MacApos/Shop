package pl.coderslab.entity;

import java.util.Comparator;

public class CategoryNameComparator implements Comparator<Category> {
    @Override
    public int compare(Category firstCategory, Category secondCategory) {
        return String.CASE_INSENSITIVE_ORDER.compare(firstCategory.getName(), secondCategory.getName());
    }
}
