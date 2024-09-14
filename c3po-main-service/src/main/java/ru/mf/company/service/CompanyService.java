package ru.mf.company.service;

import java.util.List;


public interface CompanyService<C, U, D> {

    D create(C dto);

    D update(U dto);

    void delete(D dto);

    D getById(Long id);

    List<D> findAll();

    List<D> findAll(String filterText);

}
