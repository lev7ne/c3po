package ru.mf.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mf.company.CompanyMapper;
import ru.mf.company.CompanyCreateDto;
import ru.mf.company.CompanyUpdateDto;
import ru.mf.company.CompanyViewDto;
import ru.mf.company.repository.CompanyRepository;
import ru.mf.company.service.CompanyService;
import ru.mf.company.specification.CompanyJpaSpecification;
import ru.mf.exception.NotFoundException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CompanyViewService implements CompanyService<CompanyCreateDto, CompanyUpdateDto, CompanyViewDto> {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyJpaSpecification companyJpaSpecification;

    @Transactional
    @Override
    public CompanyViewDto create(CompanyCreateDto dto) {
        var client = companyMapper.toEntity(dto);
        client = companyRepository.save(client);

        return companyMapper.toCompanyViewDto(client);
    }

    @Override
    public CompanyViewDto update(CompanyUpdateDto dto) {
        var client = companyRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Company with id=" + dto.getId() + " was not found"));
        companyMapper.update(dto, client);
        client = companyRepository.save(client);

        return companyMapper.toCompanyViewDto(client);
    }

    @Transactional
    @Override
    public void delete(CompanyViewDto dto) {
        var client = companyRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Company with id=" + dto.getId() + " was not found"));

        companyRepository.delete(client);
    }

    @Transactional(readOnly = true)
    @Override
    public CompanyViewDto getById(Long id) {
        var client = companyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Company with id=" + id + " was not found"));

        return companyMapper.toCompanyViewDto(client);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyViewDto> findAll() {
        var clients = companyRepository.findAll();

        return companyMapper.toCompanyViewDtos(clients);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyViewDto> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return findAll();
        } else {
            var spec = companyJpaSpecification.searchSpec(filterText);
            var users = companyRepository.findAll(spec);
            return companyMapper.toCompanyViewDtos(users);
        }
    }

}
