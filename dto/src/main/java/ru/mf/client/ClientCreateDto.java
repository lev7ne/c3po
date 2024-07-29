package ru.mf.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mf.annotation.StartsWith62;
import ru.mf.annotation.StartsWith7;


@Getter
@Setter
@ToString
public class ClientMoCreateDto {
    @NotBlank
    @Size(min = 2, max = 250)
    private String orgName;
    private long inn;
    @NotBlank
    @Size(min = 2, max = 250)
    private String tenant;
    @StartsWith7
    private Long personalAccount;
    @StartsWith62
    private Long msisdn;
    private boolean vip;
    private long userId;
}
