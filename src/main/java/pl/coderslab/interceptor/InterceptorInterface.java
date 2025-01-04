package pl.coderslab.interceptor;

import pl.coderslab.entity.Category;

public interface InterceptorInterface<T> {
    T findById(Long id);
}
