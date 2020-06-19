package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ExtraServiceNotFoundException extends AppBaseException {

    public static final String SERVICE_NOT_FOUND = "error.extraservice.not.found";

    public ExtraServiceNotFoundException() {
        super(SERVICE_NOT_FOUND);
    }

    public ExtraServiceNotFoundException(String message) {
        super(message);
    }

    public ExtraServiceNotFoundException(Throwable cause) {
        super(SERVICE_NOT_FOUND, cause);
    }
}
