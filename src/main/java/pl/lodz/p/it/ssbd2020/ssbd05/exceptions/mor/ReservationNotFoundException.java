package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;


public class ReservationNotFoundException extends AppBaseException {

    public static final String RESERVATION_NOT_FOUND = "error.reservation.not.found";

    public ReservationNotFoundException() {
        super(RESERVATION_NOT_FOUND);
    }

    public ReservationNotFoundException(String message) {
        super(message);
    }

    public ReservationNotFoundException(Throwable cause) {
        super(RESERVATION_NOT_FOUND, cause);
    }

}
