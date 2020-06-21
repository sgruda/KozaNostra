package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego AddHallEndpoint, pośredniczącego
 * przy dodawaniu sali przez użytkownika o poziomie dostępu menadżer
 */
@Local
public interface AddHallEndpointLocal {

    /**
     * Metoda odpowiedzialna za dodawanie sali
     *
     * @param hallDTO Obiekt transferowy typu HallDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void addHall(HallDTO hallDTO) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za pobieranie nazw wszystkich dostępnych typów imprez
     *
     * @return Lista nazw typów imprez
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<String> getAllEventTypes() throws AppBaseException;

    /**
     * Metoda odpowiedzialna za pobieranie wszystkich dostępnych adresów
     *
     * @return Lista obiektów transferowych typu AddressDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<AddressDTO> getAllAddresses() throws AppBaseException;
}
