package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego EditExtraServiceEndpoint wykorzystywanego do edycji usługi dodatkowej
 */
@Local
public interface EditExtraServiceEndpointLocal {

    /**
     * Metoda odpowiedzialna za pobranie usługi dodatkowej o podanej nazie
     *
     * @param name nazwa usługi dodatkowej
     * @return obiekt typu ExtraServiceDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    ExtraServiceDTO getExtraServiceByName(String name) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za edycję  usługi dodatkowej
     *
     * @param extraServiceDTO obiekt typu ExtraServiceDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void editExtraService(ExtraServiceDTO extraServiceDTO) throws AppBaseException;
}
