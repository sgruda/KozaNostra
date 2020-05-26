package pl.lodz.p.it.ssbd2020.ssbd05.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AppBaseException extends Exception {
    static final public String KEY_DEFAULT = "error.default";

    public AppBaseException() {
        super();
    }

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable cause) {
        super(KEY_DEFAULT, cause);
    }

}
