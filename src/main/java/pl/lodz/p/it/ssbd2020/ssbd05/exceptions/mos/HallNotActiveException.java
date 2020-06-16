package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class HallNotActiveException extends AppBaseException {
    public static final String HALL_NOT_ACTIVE = "error.hall.not.active";

    public HallNotActiveException() {
        super(HALL_NOT_ACTIVE);
    }

    public HallNotActiveException(String message) {
        super(message);
    }

    public HallNotActiveException(String message, Throwable cause) {
        super(HALL_NOT_ACTIVE, cause);
    }
}
