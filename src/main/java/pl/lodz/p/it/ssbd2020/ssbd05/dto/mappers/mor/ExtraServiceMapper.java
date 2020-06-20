package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;

import java.util.List;

/**
 * Interfejs konwertujący pomiędzy obiektami klas ExtraService i ExtraServiceDTO,
 * którego implementacja jest generowana poprzez bibliotekę MapStruct w trakcie kompilacji.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExtraServiceMapper {

    /**
     * Instancja implementacji interfejsu pobierana poprzez klasę narzędziową Mappers.
     */
    ExtraServiceMapper INSTANCE = Mappers.getMapper(ExtraServiceMapper.class);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy ExtraServiceDTO na ExtraService.
     *
     * @param extraServiceDTO Obiekt klasy ExtraServiceDTO.
     * @return Obiekt klasy ExtraService.
     */
    ExtraService createNewExtraService(ExtraServiceDTO extraServiceDTO);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy ExtraService na ExtraServiceDTO.
     *
     * @param extraService Obiekt klasy ExtraService.
     * @return Obiekt klasy ExtraServiceDTO.
     */
    ExtraServiceDTO toExtraServiceDTO(ExtraService extraService);

    /**
     * Metoda odpowiedzialna za edycję obiektu klasy ExtraService na podstawie zmian w obiekcie ExtraServiceDTO.
     *
     * @param extraServiceDTO Obiekt klasy ExtraServiceDTO.
     * @param extraService    Obiekt klasy ExtraService.
     */
    void updateExtraServiceFromDTO(ExtraServiceDTO extraServiceDTO, @MappingTarget ExtraService extraService);

    /**
     * Metoda odpowiedzialna za konwersję listy obiektów klasy ExtraService na listę obiektów ExtraServiceDTO.
     *
     * @param extraServiceCollection Lista obiektów klasy ExtraService.
     * @return Lista obiektów klasy ExtraServiceDTO.
     */
    List<ExtraServiceDTO> toExtraServiceDTOList(List<ExtraService> extraServiceCollection);
}
