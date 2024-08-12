package ru.mf.client.service;

import ru.mf.client.ClientViewDto;

import java.util.List;


public interface ClientViewService {


    void saveClient(ClientViewDto dto);

    void deleteClient(ClientViewDto dto);

    ClientViewDto getClientById(Long id);

    List<ClientViewDto> findAllClients();

    List<ClientViewDto> findAllClients(String filterText);
}
