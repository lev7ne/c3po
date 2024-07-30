package ru.mf.client.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mf.client.ClientCreateDto;
import ru.mf.client.ClientDto;
import ru.mf.client.ClientMapper;
import ru.mf.client.repository.ClientMoRepository;
import ru.mf.client.service.ClientService;
import ru.mf.client.specification.ClientJpaSpecification;
import ru.mf.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientMoRepository clientMoRepository;
    private final ClientMapper clientMapper;
    private final ClientJpaSpecification clientJpaSpecification;

    @Transactional
    @Override
    public ClientDto saveClient(ClientCreateDto dto) {
        var client = clientMapper.toEntity(dto);
        return clientMapper.toDto(clientMoRepository.save(client));
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDto getClientById(long id) {
        var client = clientMoRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Client with id=" + id + " was not found"));
        return clientMapper.toDto(client);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> findAllClients() {
        var clients = clientMoRepository.findAll();
        return clientMapper.toDtos(clients);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> findAllClients(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return findAllClients();
        } else {
            var spec = clientJpaSpecification.searchSpec(filterText);
            var users = clientMoRepository.findAll(spec);
            return clientMapper.toDtos(users);
        }
    }

}
