package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego ListHallsEndpoint, pośredniczącego
 * przy wyświetlaniu wszystkich sal w systemie oraz filtrowaniu ich
 */
@Local
public interface ListHallsEndpointLocal {

    /**
     * Metoda odpowiedzialna za pobranie wszystkich sal w systemie
     *
     * @return Lista obiektów HallDTO
     * @throws AppBaseException podstawowy wyjąek aplikacyjny
     */
    List<HallDTO> getAllHalls() throws AppBaseException;

    /**
     * Metoda odpowiedzialna za filtrowanie listy sal zgodnie z podanym ciągiem znaków
     *
     * @param hallFilter Ciąg znaków do filtrowania
     * @return Lista obiektów HallDTO
     * @throws AppBaseException podstawowy wyjąek aplikacyjny
     */
    List<HallDTO> getFilteredHalls(String hallFilter) throws AppBaseException;
}
