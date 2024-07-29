package ru.mf.client;

import org.mapstruct.*;
import ru.mf.client.model.ClientMo;

import java.util.List;


@Mapper(
//        uses = UserMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClientMoMapper {

    ClientMo toEntity(ClientMoCreateDto dto);

//    @Mapping(target = "ccc", source = "ccc.lastName")
    ClientMoDto toDto(ClientMo clientMo);

    List<ClientMoDto> toDtos(List<ClientMo> ClientsMo);
}

