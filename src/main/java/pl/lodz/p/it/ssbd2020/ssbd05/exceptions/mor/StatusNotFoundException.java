package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class StatusNotFoundException extends AppBaseException {

    public static final String STATUS_NOT_FOUND = "error.status.not.found";

    public StatusNotFoundException() {
        super(STATUS_NOT_FOUND);
    }

    public StatusNotFoundException(String message) {
        super(message);
    }

    public StatusNotFoundException(String message, Throwable cause) {
        super(cause);
    }
}