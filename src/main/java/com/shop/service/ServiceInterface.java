package com.shop.service;

public interface ServiceInterface<T> {
    boolean existsById(Long id);

    default boolean existsBy(String property) {
        return existsById(Long.valueOf(property));
    }

}
