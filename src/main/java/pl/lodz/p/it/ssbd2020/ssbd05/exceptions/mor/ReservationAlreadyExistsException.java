package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ReservationAlreadyExistsException extends AppBaseException {
    static final public String KEY_RESERVATION_EXISTS= "error.reservation.exists";


    public ReservationAlreadyExistsException() {
        super(KEY_RESERVATION_EXISTS);
    }

    public ReservationAlreadyExistsException(String message) {
        super(message);
    }

    public ReservationAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
