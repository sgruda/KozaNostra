package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany, gdy dana rezerwacja nie zostanie odnaleziona.
 */
public class ReservationNotFoundException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String RESERVATION_NOT_FOUND = "error.reservation.not.found";

    /**
     * Konstruktor bezparametrowy.
     */
    public ReservationNotFoundException() {
        super(RESERVATION_NOT_FOUND);
    }

    /**
     * Konstruktor z przyczyną.
     *
     * @param cause   Obiekt klasy Throwable będący przyczyną rzucanego wyjątku.
     */
    public ReservationNotFoundException(Throwable cause) {
        super(RESERVATION_NOT_FOUND, cause);
    }

}
