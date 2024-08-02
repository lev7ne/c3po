package ru.mf.client.service;

import java.util.List;


public interface ClientViewService<T> {
    T saveClient(T dto);

    T updateClient(T dto);

    void deleteClient(T dto);

    T getClientById(Long id);

    List<T> findAllClients();

    List<T> findAllClients(String filterText);
}
