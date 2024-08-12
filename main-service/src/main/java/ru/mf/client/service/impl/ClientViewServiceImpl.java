package ru.mf.client.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mf.client.ClientMapper;
import ru.mf.client.ClientViewDto;
import ru.mf.client.repository.ClientRepository;
import ru.mf.client.service.ClientViewService;
import ru.mf.client.specification.ClientJpaSpecification;
import ru.mf.exception.NotFoundException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClientViewServiceImpl implements ClientViewService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientJpaSpecification clientJpaSpecification;

    @Transactional
    @Override
    public void saveClient(ClientViewDto dto) {
        var client = clientMapper.toEntity(dto);
        clientRepository.save(client);
    }

    @Transactional
    @Override
    public void deleteClient(ClientViewDto dto) {
        var client = clientRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Client with id=" + dto.getId() + " was not found"));

        clientRepository.delete(client);
    }

    @Transactional(readOnly = true)
    @Override
    public ClientViewDto getClientById(Long id) {
        var client = clientRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Client with id=" + id + " was not found"));

        return clientMapper.toViewDto(client);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientViewDto> findAllClients() {
        var clients = clientRepository.findAll();

        return clientMapper.toViewDtos(clients);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientViewDto> findAllClients(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return findAllClients();
        } else {
            var spec = clientJpaSpecification.searchSpec(filterText);
            var users = clientRepository.findAll(spec);
            return clientMapper.toViewDtos(users);
        }
    }

}
