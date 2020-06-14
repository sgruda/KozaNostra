package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego AddExtraServiceEndpoint, pośredniczącemu
 * przy tworzeniu nowej usługi dodatkowej
 */
@Local
public interface AddExtraServiceEndpointLocal {

    /**
     * Metoda odpowiedzialna za utworzenie nowej usługi dodatkowej
     *
     * @param extraServiceDTO obiekt typu ExtraServiceDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void addExtraService(ExtraServiceDTO extraServiceDTO) throws AppBaseException;
}
