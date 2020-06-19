package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class HallAlreadyExistsException extends AppBaseException {

    public static final String KEY_HALL_PROBLEM = "error.hall.exists";

    public HallAlreadyExistsException() {
        super(KEY_HALL_PROBLEM);
    }

    public HallAlreadyExistsException(String message) {
        super(message);
    }

    public HallAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
