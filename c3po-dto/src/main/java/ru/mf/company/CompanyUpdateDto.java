package ru.mf.company;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
public class CompanyUpdateDto extends BaseCompanyDto {

    private Long id;

}
