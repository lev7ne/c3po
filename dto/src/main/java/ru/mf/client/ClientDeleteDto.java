package ru.mf.client;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import ru.mf.annotation.StartsWith62;
import ru.mf.annotation.StartsWith7;

public class ClientMoDeleteDto {
    @NotBlank
    @Size(min = 2, max = 250)
    private String orgName;
    @NotBlank
    @Size(min = 10, max = 12)
    private String inn;
    @NotBlank
    @Size(min = 2, max = 250)
    private String tenant;
    @StartsWith7
    private long personalAccount;
    @StartsWith62
    private long msisdn;
}
