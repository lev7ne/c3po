package ru.mf.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mf.user.UserDto;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class ClientMoDto {
    private long id;
    private String orgName;
    private long inn;
    private String tenant;
    private int personalAccount;
    private long msisdn;
    private boolean vip;
    private UserDto ccc;
    private LocalDateTime createdDate;
}
