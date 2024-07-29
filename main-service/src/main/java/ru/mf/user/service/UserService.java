package ru.mf.user.service;

import ru.mf.user.UserCreateDto;
import ru.mf.user.UserDto;

import java.util.List;


public interface UserService {
    UserDto create(UserCreateDto createDto);

    UserDto getByFirstName(String firstName);

    List<UserDto> findAll();
}
