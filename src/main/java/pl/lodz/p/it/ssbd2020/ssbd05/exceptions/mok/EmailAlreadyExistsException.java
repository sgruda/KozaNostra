package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class EmailAlreadyExistsException  extends AppBaseException {
    static final public String KEY_ACCOUNT_EMAIL_PROBLEM = "error.account.email.exists";


    public EmailAlreadyExistsException() {
        super();
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException(Throwable cause) {
        super(KEY_ACCOUNT_EMAIL_PROBLEM, cause);
    }
}
