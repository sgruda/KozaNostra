package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.AverageGuestNumberDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.AverageGuestNumber;

/**
 * Interfejs konwertujący pomiędzy obiektami klas AverageGuestNumber i AverageGuestNumberDTO,
 * którego implementacja jest generowana poprzez bibliotekę MapStruct w trakcie kompilacji.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AverageGuestNumberMapper {

    /**
     * Instancja implementacji interfejsu pobierana poprzez klasę narzędziową Mappers.
     */
    AverageGuestNumberMapper INSTANCE = Mappers.getMapper(AverageGuestNumberMapper.class);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy AverageGuestNumber na AverageGuestNumberDTO.
     *
     * @param averageGuestNumber Obiekt klasy AverageGuestNumber.
     * @return Obiekt klasy AverageGuestNumberDTO.
     */
    AverageGuestNumberDTO toAverageGuestNumberDTO(AverageGuestNumber averageGuestNumber);
}
