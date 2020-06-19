package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ExtraServiceAlreadyExistsException extends AppBaseException {
    static final public String KEY_EXTRA_SERVICE_EXISTS= "error.extraservice.exists";


    public ExtraServiceAlreadyExistsException() {
        super(KEY_EXTRA_SERVICE_EXISTS);
    }

    public ExtraServiceAlreadyExistsException(String message) {
        super(message);
    }

    public ExtraServiceAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
