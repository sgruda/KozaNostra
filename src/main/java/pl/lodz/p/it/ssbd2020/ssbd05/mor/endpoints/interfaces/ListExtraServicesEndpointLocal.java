package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego ListExtraServicesEndpoint, pośredniczącego
 * przy wyświetlaniu wszystkich usług dodatkowych w systemie oraz zmianie ich aktywności
 */
@Local
public interface ListExtraServicesEndpointLocal {

    /**
     * Metoda odpowiedzialna za pobranie wszystkich usług dodatkowych w systemie
     *
     * @return Lista obiektów typu ExtraServiceDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException;

    /**
     * Metoda odpowiedzialna za zmianę aktywności wybranej usługi dodatkowej
     *
     * @param extraServiceDTO obiekt typu extraServiceDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void changeActivity(ExtraServiceDTO extraServiceDTO) throws AppBaseException;
}
