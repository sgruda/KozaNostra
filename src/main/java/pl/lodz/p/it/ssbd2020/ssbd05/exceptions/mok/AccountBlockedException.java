package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AccountBlockedException extends AppBaseException {
    public AccountBlockedException() {super();}

    public AccountBlockedException(String message) {
        super(message);
    }

    public AccountBlockedException(String message, Throwable cause) {
        super(message, cause);
    }
}
