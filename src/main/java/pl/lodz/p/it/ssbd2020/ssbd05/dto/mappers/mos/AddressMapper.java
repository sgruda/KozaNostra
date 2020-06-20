package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;

import java.util.Collection;

/**
 * Interfejs konwertujący pomiędzy obiektami klas Address i AddressDTO,
 * którego implementacja jest generowana poprzez bibliotekę MapStruct w trakcie kompilacji.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    /**
     * Instancja implementacji interfejsu pobierana poprzez klasę narzędziową Mappers.
     */
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy Address na AddressDTO.
     *
     * @param address Obiekt klasy Address.
     * @return Obiekt klasy AddressDTO.
     */
    AddressDTO toAddressDTO(Address address);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy AddressDTO na Address.
     *
     * @param addressDTO Obiekt klasy AddressDTO.
     * @return Obiekt klasy Address.
     */
    Address createNewAddress(AddressDTO addressDTO);

    /**
     * Metoda odpowiedzialna za konwersję kolekcji obiektów klasy Address na kolekcję obiektów AddressDTO.
     *
     * @param addressCollection Kolekcja obiektów klasy Address.
     * @return Kolekcja obiektów klasy AddressDTO.
     */
    Collection<AddressDTO> toAddressDTOCollection(Collection<Address> addressCollection);
}
