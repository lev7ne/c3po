package ru.mf.client;

import org.mapstruct.*;
import ru.mf.client.model.Client;

import java.util.List;


@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClientMapper {

    Client toNewEntity(ClientCreateDto dto);

    ClientDto toDto(Client entity);

    List<ClientDto> toDtos(List<Client> entities);

    void update(ClientUpdateDto dto, @MappingTarget Client entity);


    Client toEntity(ClientViewDto dto);

    ClientViewDto toViewDto(Client entity);

    List<ClientViewDto> toViewDtos(List<Client> entities);

    void update(ClientViewDto dto, @MappingTarget Client entity);

}

