package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany przy próbie zmiany statusu rezerwacji, która została wcześniej zakończona.
 */
public class ReservationStatusFinishedException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String RESERVATION_STATUS_FINISHED = "error.reservation.status.finished";

    /**
     * Konstruktor bezparametrowy.
     */
    public ReservationStatusFinishedException() {
        super(RESERVATION_STATUS_FINISHED);
    }
    public ReservationStatusFinishedException(String messege) {
        super(messege);
    }
}