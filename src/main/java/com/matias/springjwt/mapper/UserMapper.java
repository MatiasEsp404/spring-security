package com.matias.springjwt.mapper;

import com.matias.springjwt.dto.request.RegisterRequest;
import com.matias.springjwt.dto.response.RegisterResponse;
import com.matias.springjwt.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity toEntity(RegisterRequest request);

    RegisterResponse toResponse(UserEntity entity);

}