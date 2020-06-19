package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.AverageGuestNumberDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.AverageGuestNumber;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AverageGuestNumberMapper {

    AverageGuestNumberMapper INSTANCE = Mappers.getMapper(AverageGuestNumberMapper.class);

    AverageGuestNumberDTO toAverageGuestNumberDTO(AverageGuestNumber averageGuestNumber);
}
