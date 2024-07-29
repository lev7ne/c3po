package ru.mf.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Getter
@Setter
@ToString
public class UserDto {
    private UUID id;
    private String firstName;
}
