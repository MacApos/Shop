package com.shop.mapper;

import com.shop.entity.User;
import com.shop.entity.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User> {
    void updateDTO(User source, @MappingTarget UserDTO userDTO);
}
