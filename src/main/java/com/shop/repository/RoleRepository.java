package com.shop.repository;

import com.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shop.entity.Role;
import com.shop.entity.User;

import java.util.List;

@Repository
public interface RoleRepository extends
        JpaRepository<Role, Long>
//        BaseRepository<Role, Long>
{
    Role findByName(String authority);

    List<Role> findByUser(User user);
}


