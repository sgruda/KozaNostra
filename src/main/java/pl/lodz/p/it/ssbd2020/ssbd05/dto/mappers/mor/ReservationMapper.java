package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);


    ReservationDTO toReservationDTO(Reservation Reservation); //do wyswietlania

//    Reservation createNewReservation(ReservationDTO reservationDTO); //do tworzenia nowych

    void updateReservationFromDTO(ReservationDTO reservationDTO, @MappingTarget Reservation reservation); //do update'u, nie ustawia accessLevelCollection

    Collection<ReservationDTO> toReservationDTOCollection(Collection<Reservation> reservationCollection); //do wyswietlania listy

    //metoda uzywana w toAccountDTO
    default Collection<String> toExtraServiceStringCollection(Collection<ExtraService> extraServiceCollection) {
        return extraServiceCollection.stream()
                .map(ExtraService::getServiceName)
                .collect(Collectors.toList());
    }

    //metoda uzywana w createNewAccount i updateAccountFromDTO
    default Collection<ExtraService> toExtraServiceCollection(Collection<String> extraServiceCollection) {
        return new ArrayList<>();
    }
}

