package com.shop.repository;

import com.shop.entity.Identifiable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository< T, ID>  extends JpaRepository<T, ID>{
//    void update(T source, T target);
}
