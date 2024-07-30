package ru.mf.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
