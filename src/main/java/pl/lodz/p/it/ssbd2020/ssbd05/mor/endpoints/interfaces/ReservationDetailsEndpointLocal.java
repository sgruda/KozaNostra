package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego ReservationDetailsEndpoint pośredniczącemu
 * przy wyświetlaniu szczegółów wybranej rezerwacji
 */
@Local
public interface ReservationDetailsEndpointLocal {
    /**
     * Metoda odpowiadająca za pobranie szczegółów wybranej rezerwacji
     *
     * @param number numer rezerwacji
     * @return rezerwacja
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    ReservationDTO getReservationByNumber(String number) throws AppBaseException;
}
