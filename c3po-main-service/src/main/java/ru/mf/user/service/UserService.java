package ru.mf.user.service;

import ru.mf.user.UserDto;

import java.util.List;


public interface UserService {
    UserDto getUserById(long id);

    UserDto saveUser(UserDto createDto);

    List<UserDto> findAllUsers();

    List<UserDto> findAllUsers(String filterText);

    long countUser();

    void deleteUser(UserDto dto);

}
