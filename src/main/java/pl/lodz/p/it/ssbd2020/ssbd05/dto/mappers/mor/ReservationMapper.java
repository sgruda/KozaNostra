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

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.WITH_SECONDS;

/**
 * Interfejs konwertujący pomiędzy obiektami klas Reservation i ReservationDTO,
 * którego implementacja jest generowana poprzez bibliotekę MapStruct w trakcie kompilacji.
 */
@Mapper(uses = ClientMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    /**
     * Instancja implementacji interfejsu pobierana poprzez klasę narzędziową Mappers.
     */
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy Reservation na ReservationDTO.
     *
     * @param reservation Obiekt klasy Reservation.
     * @return Obiekt klasy ReservationDTO.
     */
    @Mapping(target = "startDate", dateFormat = WITH_SECONDS)
    @Mapping(target = "endDate", dateFormat = WITH_SECONDS)
    @Mapping(target = "clientDTO", source = "client")
    @Mapping(target = "statusName", source = "status")
    @Mapping(target = "eventTypeName", source = "eventType")
    @Mapping(target = "extraServiceCollection", source = "extra_service")
    @Mapping(target="hallName", source = "hall")
    ReservationDTO toReservationDTO(Reservation reservation);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy ReservationDTO na Reservation.
     *
     * @param reservationDTO Obiekt klasy ReservationDTO.
     * @return Obiekt klasy Reservation.
     */
    @Mapping(target = "startDate", dateFormat = WITH_SECONDS)
    @Mapping(target = "endDate", dateFormat = WITH_SECONDS)
    Reservation createNewReservation(ReservationDTO reservationDTO);

    /**
     * Metoda odpowiedzialna za edycję obiektu klasy Reservation na podstawie zmian w obiekcie ReservationDTO.
     *
     * @param reservationDTO Obiekt klasy ReservationDTO.
     * @param reservation    Obiekt klasy Reservation.
     */
    @Mapping(target = "startDate", dateFormat = WITH_SECONDS)
    @Mapping(target = "endDate", dateFormat = WITH_SECONDS)
    void updateReservationFromDTO(ReservationDTO reservationDTO, @MappingTarget Reservation reservation);

    /**
     * Metoda odpowiedzialna za konwersję kolekcji obiektów klasy Reservation na kolekcję obiektów ReservationDTO.
     *
     * @param reservationCollection Kolekcja obiektów klasy Reservation.
     * @return Kolekcja obiektów klasy ReservationDTO.
     */
    Collection<ReservationDTO> toReservationDTOCollection(Collection<Reservation> reservationCollection);

    /**
     * Metoda odpowiedzialna za konwersję kolekcji obiektów klasy ExtraService na kolekcję ich nazw.
     * Zdefiniowanie domyślnej implementacji jest konieczne w celu wykorzystania jej w metodzie toReservationDTO.
     *
     * @param extraServiceCollection Kolekcja obiektów klasy ExtraService.
     * @return Kolekcja nazw usług dodatkowych.
     */
    default Collection<String> toExtraServiceDTOCollection(Collection<ExtraService> extraServiceCollection) {
        return extraServiceCollection.stream()
                .map(ExtraService::getServiceName)
                .collect(Collectors.toList());
    }

    /**
     * Metoda odpowiedzialna za konwersję kolekcji nazw usług dodatkowych na kolekcję obiektów klasy ExtraService.
     * Zdefiniowanie domyślnej implementacji jest konieczne w celu wykorzystania jej w metodach createNewReservation
     * i updateReservationFromDTO, jednak po ich wywołaniu należy ręcznie ustawić kolekcję ExtraService.
     *
     * @param extraServiceStringCollection Kolekcja nazw usług dodatkowych.
     * @return Kolekcja obiektów klasy ExtraService.
     */
    default Collection<ExtraService> toExtraServiceCollection(Collection<String> extraServiceStringCollection) {
        return new ArrayList<>();
    }

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy Client na ciąg znaków będący jego nazwą użytkownika.
     *
     * @param client Obiekt klasy Client.
     * @return Nazwa użytkownika.
     */
    default String map(Client client) {
        return client.getAccount().getLogin();
    }

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy Status na ciąg znaków będący jego nazwą.
     *
     * @param status Obiekt klasy Status.
     * @return Nazwa statusu rezerwacji.
     */
    default String map(Status status) {
        return status.getStatusName();
    }

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy EventType na ciąg znaków będący jego nazwą.
     *
     * @param eventType Obiekt klasy EventType.
     * @return Nazwa typu imprezy.
     */
    default String map(EventType eventType) {
        return eventType.getTypeName();
    }

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy Hall na ciąg znaków będący jego nazwą.
     *
     * @param hall Obiekt klasy Hall.
     * @return Nazwa sali.
     */
    default String map(Hall hall) {
        return hall.getName();
    }
}
