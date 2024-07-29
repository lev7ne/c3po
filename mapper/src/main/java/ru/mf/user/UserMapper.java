package ru.mf.user.mapper;

import org.mapstruct.*;
import ru.mf.user.dto.UserDto;
import ru.mf.user.dto.UserCreateDto;
import ru.mf.user.model.User;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class UserMapper {
    public abstract User toEntity(UserCreateDto dto);

    public abstract UserDto toDto(User user);
}
