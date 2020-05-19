package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class HallActiveException extends AppBaseException {

    public static final String KEY_HALL_ACTIVE_PROBLEM = "error.hall.active";

    public HallActiveException() {
        super();
    }

    public HallActiveException(String message) {
        super(message);
    }

    public HallActiveException(String message, Throwable cause) {
        super(KEY_HALL_ACTIVE_PROBLEM, cause);
    }
}
