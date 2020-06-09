package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Status;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.WITHOUT_SECONDS;
import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.WITH_SECONDS;

@Mapper(uses = ClientMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(target = "startDate", dateFormat = WITH_SECONDS)
    @Mapping(target = "endDate", dateFormat = WITH_SECONDS)
//    @Mapping(target = "clientLogin", source = "client")
    @Mapping(target = "clientDTO", source = "client")
    @Mapping(target = "statusName", source = "status")
    @Mapping(target = "eventTypeName", source = "eventType")
    @Mapping(target = "extraServiceCollection", source = "extra_service")
    @Mapping(target="hallName", source = "hall")
    ReservationDTO toReservationDTO(Reservation reservation); //do wyswietlania

    @Mapping(target = "startDate", dateFormat = WITH_SECONDS)
    @Mapping(target = "endDate", dateFormat = WITH_SECONDS)
    Reservation createNewReservation(ReservationDTO reservationDTO); //do tworzenia nowych

    @Mapping(target = "startDate", dateFormat = WITH_SECONDS)
    @Mapping(target = "endDate", dateFormat = WITH_SECONDS)
    void updateReservationFromDTO(ReservationDTO reservationDTO, @MappingTarget Reservation reservation); //do update'u, nie ustawia extraServiceCollection

    Collection<ReservationDTO> toReservationDTOCollection(Collection<Reservation> reservationCollection); //do wyswietlania listy

    //metoda uzywana w toReservationDTO
    default Collection<String> toExtraServiceDTOCollection(Collection<ExtraService> extraServiceCollection) {
        return extraServiceCollection.stream()
                .map(ExtraService::getServiceName)
                .collect(Collectors.toList());
    }

    //metoda uzywana w createNewReservation i updateReservationFromDTO
    default Collection<ExtraService> toExtraServiceCollection(Collection<String> extraServiceStringCollection) {
        return new ArrayList<>();
    }

    default String map(Client client) {
        return client.getAccount().getLogin();
    }
    default String map(Status status) {
        return status.getStatusName();
    }
    default String map(EventType eventType) {
        return eventType.getTypeName();
    }
    default String map(Hall hall){return hall.getName();}
}
