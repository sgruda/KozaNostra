package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExtraServiceMapper {

    ExtraServiceMapper INSTANCE = Mappers.getMapper(ExtraServiceMapper.class);

    ExtraService createNewExtraService(ExtraServiceDTO extraServiceDTO);

    List<ExtraServiceDTO> toExtraServiceDTOList(List<ExtraService> extraServiceCollection);
}
