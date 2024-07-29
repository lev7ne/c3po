package ru.mf.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mf.user.UserCreateDto;
import ru.mf.user.UserDto;
import ru.mf.user.UserMapper;
import ru.mf.user.repository.UserRepository;
import ru.mf.user.service.UserService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto create(UserCreateDto createDto) {
        var user = userMapper.toEntity(createDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getByFirstName(String firstName) {
        return userMapper.toDto(userRepository.findByFirstName(firstName));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }
}
