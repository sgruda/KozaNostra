package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego HallDetailsEndpoint, pośredniczącemu
 * przy wyświetlaniu szczegółów wybranej sali
 */
@Local
public interface HallDetailsEndpointLocal {

    /**
     * Meotda odpowiedzialna za pobieranie pojedynczej sali na podstawie jej nazwy
     *
     * @param name Nazwa sali
     * @return Obiekt typu HallDTO reprezentujący salę
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    HallDTO getHallByName(String name) throws AppBaseException;
    void changeActivity(HallDTO hall) throws AppBaseException;
}
