package ru.mf.client.service;

import ru.mf.client.ClientCreateDto;
import ru.mf.client.ClientDto;


import java.util.List;

public interface ClientService {
    ClientDto saveClient(ClientCreateDto dto);

    ClientDto getClientById(long id);

    List<ClientDto> findAllClients();

    List<ClientDto> findAllClients(String filterText);
}
