package pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego EditHallEndpoint, pośredniczącemu
 * przy edycji sali przez użytkownika o poziomie dostępu menadżer
 */
@Local
public interface EditHallEndpointLocal {

    /**
     * Meotda odpowiedzialna za pobieranie pojedynczej sali na podstawie jej nazwy
     *
     * @param name Nazwa sali
     * @return Obiekt typu HallDTO reprezentujący salę
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    HallDTO getHallByName(String name) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za edycję sali
     *
     * @param hallDTO Obiekt transferowy typu HallDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void editHall(HallDTO hallDTO) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za pobieranie nazw wszystkich dostępnych typów imprez
     *
     * @return Lista nazw typów imprez
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<String> getAllEventTypes() throws AppBaseException;

//    void changeActivity(HallDTO hallDTO) throws AppBaseException;
}
