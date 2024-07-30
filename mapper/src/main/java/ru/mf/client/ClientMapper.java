package ru.mf.client;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.mf.client.model.Client;

import java.util.List;


@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClientMapper {
    Client toEntity(ClientCreateDto dto);

    ClientDto toDto(Client entity);

    List<ClientDto> toDtos(List<Client> entities);
}

