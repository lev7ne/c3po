package ru.mf.company;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString(callSuper = true)
public class CompanyDto extends BaseCompanyDto {

    private Long id;

    private LocalDate createdDate;

}
