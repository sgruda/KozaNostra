package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ReservationNotFoundException extends AppBaseException {

    public static final String RESERVATION_NOT_FOUND = "error.reservation.not.found";

    public ReservationNotFoundException() {
        super();
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }

    public ReservationNotFoundException(String message, Throwable cause) {
        super(RESERVATION_NOT_FOUND, cause);
    }
}
