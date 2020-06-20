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

/**
 * Interfejs konwertujący pomiędzy obiektami klas Hall i HallDTO,
 * którego implementacja jest generowana poprzez bibliotekę MapStruct w trakcie kompilacji.
 */
@Mapper(uses = AddressMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HallMapper {

    /**
     * Instancja implementacji interfejsu pobierana poprzez klasę narzędziową Mappers.
     */
    HallMapper INSTANCE = Mappers.getMapper(HallMapper.class);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy Hall na HallDTO.
     *
     * @param hall Obiekt klasy Hall.
     * @return Obiekt klasy HallDTO.
     */
    HallDTO toHallDTO(Hall hall);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy HallDTO na Hall.
     *
     * @param hallDTO Obiekt klasy HallDTO.
     * @return Obiekt klasy Hall.
     */
    Hall createNewHall(HallDTO hallDTO);

    /**
     * Metoda odpowiedzialna za edycję obiektu klasy Hall na podstawie zmian w obiekcie HallDTO.
     *
     * @param hallDTO Obiekt klasy HallDTO.
     * @param hall    Obiekt klasy Hall.
     */
    void updateHallFromDTO(HallDTO hallDTO, @MappingTarget Hall hall);

    /**
     * Metoda odpowiedzialna za konwersję kolekcji obiektów klasy Hall na kolekcję obiektów HallDTO.
     *
     * @param hallCollection Kolekcja obiektów klasy Hall.
     * @return Kolekcja obiektów klasy HallDTO.
     */
    Collection<HallDTO> toHallDTOCollection(Collection<Hall> hallCollection);

    /**
     * Metoda odpowiedzialna za konwersję kolekcji obiektów klasy EventType na kolekcję ich nazw.
     * Zdefiniowanie domyślnej implementacji jest konieczne w celu wykorzystania jej w metodzie toHallDTO.
     *
     * @param eventTypeCollection Kolekcja obiektów klasy EventType.
     * @return Kolekcja nazw typów imprez.
     */
    default Collection<String> toEventTypeStringCollection(Collection<EventType> eventTypeCollection) {
        return eventTypeCollection.stream()
                .map(EventType::getTypeName)
                .collect(Collectors.toList());
    }

    /**
     * Metoda odpowiedzialna za konwersję kolekcji nazw typów imprez na kolekcję obiektów klasy EventType.
     * Zdefiniowanie domyślnej implementacji jest konieczne w celu wykorzystania jej w metodach createNewHall
     * i updateHallFromDTO, jednak po ich wywołaniu należy ręcznie ustawić kolekcję EventType.
     *
     * @param eventTypeStringCollection Kolekcja nazw typów imprez.
     * @return Kolekcja obiektów klasy EventType.
     */
    default Collection<EventType> toEventTypeCollection(Collection<String> eventTypeStringCollection) {
        return new ArrayList<>();
    }
}
