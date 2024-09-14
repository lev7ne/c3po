package ru.mf.company;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class CompanyViewDto extends BaseCompanyDto {

    private Long id;

    private LocalDate createdDate;

}
