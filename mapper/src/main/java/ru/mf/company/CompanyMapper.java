package ru.mf.company;

import org.mapstruct.*;
import ru.mf.company.model.Company;

import java.util.List;


@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    void update(CompanyUpdateDto dto, @MappingTarget Company entity);

    Company toEntity(CompanyCreateDto dto);

    CompanyViewDto toCompanyViewDto(Company entity);

    List<CompanyViewDto> toViewDtos(List<Company> entities);

    CompanyCreateDto toCompanyCreateDto(CompanyViewDto dto);

    CompanyUpdateDto toCompanyUpdateDto(CompanyViewDto dto);
}

