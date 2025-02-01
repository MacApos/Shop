package com.shop.mapper;

import com.shop.entity.RegistrationToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationTokenMapper extends GenericMapper<RegistrationToken> {

}
