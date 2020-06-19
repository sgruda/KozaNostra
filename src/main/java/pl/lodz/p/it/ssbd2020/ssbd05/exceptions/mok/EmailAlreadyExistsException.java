package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class EmailAlreadyExistsException  extends AppBaseException {
    static final public String KEY_ACCOUNT_EMAIL_PROBLEM = "error.account.email.exists";


    public EmailAlreadyExistsException() {
        super(KEY_ACCOUNT_EMAIL_PROBLEM);
    }

    public EmailAlreadyExistsException(String message) {
        super(KEY_ACCOUNT_EMAIL_PROBLEM);
    }

    public EmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
