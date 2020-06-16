package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(uses = AddressMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HallMapper {

    HallMapper INSTANCE = Mappers.getMapper(HallMapper.class);

    Hall toHall(HallDTO hallDTO);
    HallDTO toHallDTO(Hall hall);
    Hall createNewHall(HallDTO hallDTO);
    void updateHallFromDTO(HallDTO hallDTO, @MappingTarget Hall hall);
    Collection<HallDTO> toHallDTOCollection(Collection<Hall> hallCollection);

    default Collection<String> toEventTypeStringCollection(Collection<EventType> eventTypeCollection) {
        return eventTypeCollection.stream()
                .map(EventType::getTypeName)
                .collect(Collectors.toList());
    }

    default Collection<EventType> toEventTypeCollection(Collection<String> eventTypeStringCollection) {
        return new ArrayList<>();
    }
}
