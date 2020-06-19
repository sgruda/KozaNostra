package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.AverageGuestNumberDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego GetAggregateEndpoint, pośredniczącemu
 * przy pobieraniu z bazy obiektu encyjnego agregatu
 */
@Local
public interface GetAggregateEndpointLocal {

    /**
     * Metoda odpowiedzialna za pobranie agregatu z bazy
     *
     * @return Obiekt typu AverageGuestNumberDTO
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny
     */
    AverageGuestNumberDTO getAggregate() throws AppBaseException;
}
