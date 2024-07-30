package ru.mf.user.service;

import ru.mf.user.UserCreateDto;
import ru.mf.user.UserDeleteDto;
import ru.mf.user.UserDto;

import java.util.List;


public interface UserService {
    UserDto getUserById(long id);

    UserDto saveUser(UserCreateDto createDto);

    List<UserDto> findAllUsers();

    List<UserDto> findAllUsers(String filterText);

    long countUser();

    void deleteUser(UserDeleteDto dto);

}
