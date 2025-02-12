package com.shop.repository.impl;
//
//import jakarta.persistence.EntityManager;
//import org.springframework.data.jpa.repository.support.JpaEntityInformation;
//import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
//
//public class GenericRepository<T, ID> extends SimpleJpaRepository<T, ID>{
//
//    public GenericRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
//        super(entityInformation, entityManager);
//    }
//}

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class GenericRepository<T, ID> extends SimpleJpaRepository<T, ID> {
    public GenericRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}

//
//
////    private final EntityManager entityManager;
////    private final Map<Class<?>, Class<? extends GenericMapper<?>>> entityMappers;
////
////    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
////        super(entityInformation, entityManager);
////        this.entityManager = entityManager;
////        this.entityMappers = new HashMap<>();
////        initializeMappers();
////    }
////
////    private void initializeMappers() {
////        entityMappers.put(User.class, UserMapper.class);
////        entityMappers.put(RegistrationToken.class, RegistrationTokenMapper.class);
////    }
////
////    @Override
////    public void update(T source, T target) {
////        GenericMapper<?> mapper = Mappers.getMapper(entityMappers.get(target.getClass()));
////
////        if (mapper == null) {
////            throw new IllegalArgumentException("No mapper found for entity type: " + source.getClass());
////        }
////
////        if (mapper instanceof UserMapper userMapper) {
////            userMapper.update((User) source, (User) target);
////        } else if (mapper instanceof RegistrationTokenMapper tokenMapper) {
////            tokenMapper.update((RegistrationToken) target, (RegistrationToken) source);
////        }
////        entityManager.merge(target);
////    }
////
////    public ID getId(T entity) {
////        if (entity instanceof Identifiable) {
////            return ((Identifiable<ID>) entity).getId();
////        }
////        throw new IllegalArgumentException("Entity must implement Identifiable interface");
////    }
////}
