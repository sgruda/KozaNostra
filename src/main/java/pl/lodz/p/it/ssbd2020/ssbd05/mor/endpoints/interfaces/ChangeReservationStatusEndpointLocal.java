package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego ChangeReservationStatusEndpoint, pośredniczącego
 * przy zmianie statusu rezerwacji.
 */
@Local
public interface ChangeReservationStatusEndpointLocal {

    /**
     * Metoda odpowiedzialna za pobieranie rezerwacji na podstawie jej numeru.
     *
     * @param reservationNumber Numer rezerwacji
     * @return Obiekt typu ReservationDTO reprezentujący rezerwację
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    ReservationDTO getReservationByNumber(String reservationNumber) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za zmianę statusu rezerwacji.
     *
     * @param reservationDTO Obiekt transferowy typu ReservationDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void changeReservationStatus(ReservationDTO reservationDTO) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za anulowanie rezerwacji przez użytkownika o poziomie dostępu klient.
     *
     * @param reservationDTO Obiekt transferowy typu ReservationDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void cancelReservation(ReservationDTO reservationDTO) throws AppBaseException;
}
