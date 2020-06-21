package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego EditReservationEndpoint pośredniczącego
 * przy edycji rezerwacji
 */
@Local
public interface EditReservationEndpointLocal {

    /**
     * Metoda odpowiedzialna za pobranie rezerwacji po numerze
     *
     * @param number numer rezerwacji
     * @return obiekt rezerwacji typu ReservationDTO
     * @throws AppBaseException  podstawowy wyjątek aplikacyjny
     */
    ReservationDTO getReservationByNumber(String number) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za edycję rezerwacji
     *
     * @param reservationDTO obiekt typu ReservationDTO - rezerwacja
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void editReservation(ReservationDTO reservationDTO) throws AppBaseException;

    /**
     * Pobierz salę według podanej nazwy
     *
     * @param name nazwa sali do pobrania
     * @return obiekt sali pobrany według nazwy
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    HallDTO getHallByName(String name) throws AppBaseException;

    /**
     * Pobierz listę wszystkich usług dodatkowych
     *
     * @return lista usług dodatkowych
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<ExtraServiceDTO> getAllExtraServices() throws AppBaseException;

    /**
     * Pobierz wszystkie niedostępne terminy dla wybranej przez użytkownika sali
     *
     * @param hallName nazwa sali, w której użytkownik chce dokonać rezerwacji
     * @return lista niedostępnych terminów
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<UnavailableDate> getUnavailableDates(String hallName) throws AppBaseException;
}
