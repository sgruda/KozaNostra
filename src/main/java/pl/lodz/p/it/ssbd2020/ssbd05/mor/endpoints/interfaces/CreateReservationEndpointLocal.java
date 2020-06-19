package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego CreateReservationEndpoint pośredniczącemu
 * przy tworzeniu nowej rezerwacji
 */
@Local
public interface CreateReservationEndpointLocal {

    /**
     * Pobierz wszystkie niedostępne terminy dla wybranej przez użytkownika sali
     *
     * @param hallName nazwa sali, w której użytkownik chce dokonać rezerwacji
     * @return lista niedostępnych terminów
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<UnavailableDate> getUnavailableDates(String hallName) throws AppBaseException;

    /**
     * Utwórz nową rezerwację
     *
     * @param reservationDTO obiekt DTO rezerwacji
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void createReservation(ReservationDTO reservationDTO) throws AppBaseException;

    /**
     * Pobierz listę wszystkich usług dodatkowych
     *
     * @return lista usług dodatkowych
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException;

    /**
     * Pobierz salę według podanej nazwy
     *
     * @param hallName nazwa sali do pobrania
     * @return obiekt sali pobrany według nazwy
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    HallDTO getHallByName(String hallName) throws AppBaseException;

}
