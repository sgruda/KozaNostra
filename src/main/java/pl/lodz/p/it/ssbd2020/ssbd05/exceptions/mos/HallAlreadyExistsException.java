package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class HallAlreadyExistsException extends AppBaseException {

    public static final String KEY_HALL_PROBLEM = "error.hall.exists";

    public HallAlreadyExistsException(Throwable cause) {
        super(KEY_HALL_PROBLEM, cause);
    }

}
