package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego RemoveHallEndpoint, pośredniczącego
 * przy usuwaniu sali przez użytkownika o poziomie dostępu menadżer
 */
@Local
public interface RemoveHallEndpointLocal {
    /**
     * Metoda odpowiadająca za usuwanie sali
     *
     * @param hallDTO obiekt transferowy typu HallDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void removeHall(HallDTO hallDTO) throws AppBaseException;

    /**
     * Metoda odpowiadająca za pobranie sali
     *
     * @param hallName nazwa sali
     * @return Obiekt typu HallDTO reprezentujący salę
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    HallDTO getHallByName(String hallName) throws  AppBaseException;

}
