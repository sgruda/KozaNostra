package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ReservationStatusFinishedException extends AppBaseException {

    public static final String RESERVATION_STATUS_FINISHED = "error.reservation.status.finished";

    public ReservationStatusFinishedException() {
        super(RESERVATION_STATUS_FINISHED);
    }

    public ReservationStatusFinishedException(String message) {
        super(message);
    }

    public ReservationStatusFinishedException(Throwable cause) {
        super(RESERVATION_STATUS_FINISHED, cause);
    }

}