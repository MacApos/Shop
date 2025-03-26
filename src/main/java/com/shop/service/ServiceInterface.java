package com.shop.service;

public interface ServiceInterface<T> { // delete
    boolean existsById(Long id);

    default boolean existsBy(String property) {
        return existsById(Long.valueOf(property));
    }

}
