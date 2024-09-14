package ru.mf.company.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import ru.mf.company.CompanyCreateDto;
import ru.mf.company.CompanyMapper;
import ru.mf.company.CompanyUpdateDto;
import ru.mf.company.CompanyViewDto;
import ru.mf.company.model.Company;
import ru.mf.company.repository.CompanyRepository;
import ru.mf.company.specification.CompanyJpaSpecification;
import ru.mf.exception.NotFoundException;
import ru.mf.user.UserDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CompanyViewServiceTest {

    @Mock
    private CompanyRepository mockCompanyRepository;

    @Mock
    private CompanyMapper mockCompanyMapper;

    @Mock
    private CompanyJpaSpecification mockCompanyJpaSpecification;

    @InjectMocks
    private CompanyViewService companyViewService;

    private CompanyCreateDto companyCreateDto;
    private CompanyUpdateDto companyUpdateDto;
    private Company company;
    private CompanyViewDto companyViewDto;

    @BeforeEach
    void setUp() {
        // given
        companyCreateDto = new CompanyCreateDto();
        companyCreateDto.setOrgName("Rebel Alliance");
        companyCreateDto.setInn(1234567890L);

        UserDto userDto = new UserDto(1L, "Luke", "Skywalker", "luke.skywalker@starwars.com");
        companyCreateDto.setAppUser(userDto);

        company = new Company();
        company.setOrgName("Rebel Alliance");
        company.setInn(1234567890L);

        companyViewDto = new CompanyViewDto();
        companyViewDto.setOrgName("Rebel Alliance");

        companyUpdateDto = new CompanyUpdateDto();
        companyUpdateDto.setId(1L);
        companyUpdateDto.setOrgName("Galactic Empire");
    }

    @Test
    void create_shouldReturnCompanyViewDto() {
        // given
        given(mockCompanyMapper.toEntity(companyCreateDto)).willReturn(company);
        given(mockCompanyRepository.save(company)).willReturn(company);
        given(mockCompanyMapper.toCompanyViewDto(company)).willReturn(companyViewDto);

        // when
        CompanyViewDto result = companyViewService.create(companyCreateDto);

        // then
        assertNotNull(result);
        assertEquals("Rebel Alliance", result.getOrgName());

        verify(mockCompanyMapper, times(1)).toEntity(companyCreateDto);
        verify(mockCompanyRepository, times(1)).save(company);
        verify(mockCompanyMapper, times(1)).toCompanyViewDto(company);
    }

    @Test
    void update_shouldReturnUpdatedCompanyViewDto() {
        // given
        companyViewDto.setOrgName("Galactic Empire");

        given(mockCompanyRepository.findById(1L)).willReturn(Optional.of(company));
        doNothing().when(mockCompanyMapper).update(companyUpdateDto, company);
        given(mockCompanyRepository.save(company)).willReturn(company);
        given(mockCompanyMapper.toCompanyViewDto(company)).willReturn(companyViewDto);

        // when
        CompanyViewDto result = companyViewService.update(companyUpdateDto);

        // then
        assertNotNull(result);
        assertEquals("Galactic Empire", result.getOrgName());

        verify(mockCompanyRepository, times(1)).findById(1L);
        verify(mockCompanyMapper, times(1)).update(companyUpdateDto, company);
        verify(mockCompanyRepository, times(1)).save(company);
        verify(mockCompanyMapper, times(1)).toCompanyViewDto(company);
    }

    @Test
    void update_shouldThrowNotFoundException_whenCompanyNotFound() {
        // given
        given(mockCompanyRepository.findById(1L)).willReturn(Optional.empty());

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                companyViewService.update(companyUpdateDto));

        // then
        assertEquals("Company with id=1 was not found", exception.getMessage());

        verify(mockCompanyRepository, times(1)).findById(1L);
        verify(mockCompanyMapper, never()).update(any(), any());
        verify(mockCompanyRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeleteCompany() {
        // given
        given(mockCompanyRepository.findById(companyViewDto.getId())).willReturn(Optional.of(company));

        // when
        companyViewService.delete(companyViewDto);

        // then
        verify(mockCompanyRepository).findById(companyViewDto.getId());
        verify(mockCompanyRepository).delete(company);
    }

    @Test
    void delete_shouldThrowNotFoundException_whenCompanyDoesNotExist() {
        // given
        companyViewDto.setId(1L);
        given(mockCompanyRepository.findById(companyViewDto.getId())).willReturn(Optional.empty());

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                companyViewService.delete(companyViewDto));

        // then
        assertEquals("Company with id=1 was not found", exception.getMessage());

        verify(mockCompanyRepository).findById(companyViewDto.getId());
    }

    @Test
    void getById_shouldReturnCompanyViewDto() {
        // given
        given(mockCompanyRepository.findById(1L)).willReturn(Optional.of(company));
        given(mockCompanyMapper.toCompanyViewDto(company)).willReturn(companyViewDto);

        // when
        CompanyViewDto result = companyViewService.getById(1L);

        // then
        assertEquals(companyViewDto, result);

        verify(mockCompanyRepository).findById(1L);
    }

    @Test
    void getById_shouldThrowNotFoundException_whenCompanyDoesNotExist() {
        // given
        given(mockCompanyRepository.findById(1L)).willReturn(Optional.empty());

        // when

        // then
        assertThrows(NotFoundException.class, () -> companyViewService.getById(1L));
        verify(mockCompanyRepository).findById(1L);
    }

    @Test
    void findAll_shouldReturnListOfCompanyViewDtos() {
        // given
        List<Company> companies = List.of(company);
        List<CompanyViewDto> companyViewDtos = List.of(companyViewDto);

        given(mockCompanyRepository.findAll()).willReturn(companies);
        given(mockCompanyMapper.toCompanyViewDtos(companies)).willReturn(companyViewDtos);

        // when
        List<CompanyViewDto> result = companyViewService.findAll();

        // then
        assertEquals(companyViewDtos, result);
        verify(mockCompanyRepository).findAll();
    }

    @Test
    void findAll_withFilterText_shouldReturnFilteredCompanies() {
        // given
        String filterText = "Rebel";
        Specification<Company> spec = (root, query, cb) -> cb.conjunction();
        List<Company> companies = List.of(company);
        List<CompanyViewDto> companyViewDtos = List.of(companyViewDto);

        given(mockCompanyJpaSpecification.searchSpec(filterText)).willReturn(spec);
        given(mockCompanyRepository.findAll(spec)).willReturn(companies);
        given(mockCompanyMapper.toCompanyViewDtos(companies)).willReturn(companyViewDtos);

        // when
        List<CompanyViewDto> result = companyViewService.findAll(filterText);

        // then
        assertEquals(companyViewDtos, result);
        verify(mockCompanyJpaSpecification).searchSpec(filterText);
        verify(mockCompanyRepository).findAll(spec);
    }
}