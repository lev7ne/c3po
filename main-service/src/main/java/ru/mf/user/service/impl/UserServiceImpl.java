package ru.mf.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mf.exception.NotFoundException;
import ru.mf.user.UserDto;
import ru.mf.user.UserMapper;
import ru.mf.user.repository.UserRepository;
import ru.mf.user.service.UserService;
import ru.mf.user.specification.UserJpaSpecification;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserJpaSpecification userJpaSpecification;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(long id) {
        var user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id=" + id + " was not found"));
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDto saveUser(UserDto dto) {
        var user = userRepository.save(userMapper.toEntity(dto));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAllUsers() {
        var users = userRepository.findAll();
        return userMapper.toDtos(users);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAllUsers(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return findAllUsers();
        } else {
            var spec = userJpaSpecification.searchSpec(filterText);
            var users = userRepository.findAll(spec);
            return userMapper.toDtos(users);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public long countUser() {
        return userRepository.count();
    }

    @Transactional
    @Override
    public void deleteUser(UserDto dto) {
        var spec = userJpaSpecification.deleteUserSpec(dto);
        userRepository.delete(spec);
    }
}
