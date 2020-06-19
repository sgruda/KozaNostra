package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class NoncancelableReservationException extends AppBaseException {

    public static final String NONCANCELABLE_RESERVATION = "error.reservation.noncancelable";

    public NoncancelableReservationException() {
        super(NONCANCELABLE_RESERVATION);
    }

    public NoncancelableReservationException(String message) {
        super(message);
    }

    public NoncancelableReservationException(Throwable cause) {
        super(cause);
    }

}