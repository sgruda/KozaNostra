package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany, przy próbie anulowania rezerwacji, której status na to nie pozwala.
 */
public class NoncancelableReservationException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    public static final String NONCANCELABLE_RESERVATION = "error.reservation.noncancelable";

    /**
     * Konstruktor bezparametrowy.
     */
    public NoncancelableReservationException() {
        super(NONCANCELABLE_RESERVATION);
    }

}