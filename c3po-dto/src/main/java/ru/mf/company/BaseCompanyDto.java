package ru.mf.company;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mf.user.UserDto;


@Getter
@Setter
@ToString
public abstract class BaseCompanyDto {

    @NotEmpty
    @Size(min = 2, max = 250, message = "Название организации должно содержать не менее 3 символов!")
    private String orgName;

    @Digits(integer = 10, fraction = 0, message = "ИНН состоит минимум из 10 цифр!")
    private Long inn;

    private UserDto appUser;

}
