package com.shop.mapper;

import com.shop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User> {
//    void update(User source, @MappingTarget User target);
}
