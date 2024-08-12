package ru.mf.client;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mf.annotation.StartsWith62;
import ru.mf.annotation.StartsWith7;
import ru.mf.user.UserDto;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class ClientDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, max = 250)
    private String orgName;
    @Digits(integer = 12, fraction = 0)
    private Long inn;
    @NotEmpty
    @Size(min = 2, max = 250)
    private String tenant;
    @StartsWith7
    private Long personalAccount;
    @StartsWith62
    private Long msisdn;
    private UserDto appUser;
    private LocalDate createdDate;
}
