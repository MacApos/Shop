package com.shop.interceptor;

public interface ServiceInterface<T> {
    T findById(Long id);

    boolean existsById(Long id);

    default boolean existsBy(String property) {
        return existsById(Long.valueOf(property));
    }
}
