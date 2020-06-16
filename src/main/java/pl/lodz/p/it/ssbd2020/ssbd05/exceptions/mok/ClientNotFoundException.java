package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ClientNotFoundException extends AppBaseException {
    static final public String KEY_CLIENT_NOT_FOUND = "error.client.not.found";


    public ClientNotFoundException() {
        super();
    }

    public ClientNotFoundException(String message) {
        super(KEY_CLIENT_NOT_FOUND);
    }

    public ClientNotFoundException(Throwable cause) {
        super(KEY_CLIENT_NOT_FOUND, cause);
    }
}
