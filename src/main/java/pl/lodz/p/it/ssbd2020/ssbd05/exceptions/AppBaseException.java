package pl.lodz.p.it.ssbd2020.ssbd05.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AppBaseException extends Exception {
    static final public String KEY_DEFAULT = "error.default";

    public AppBaseException() {
        super(KEY_DEFAULT);
    }

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
