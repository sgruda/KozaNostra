package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class LoginAlreadyExistsException extends AppBaseException {
    static final public String KEY_ACCOUNT_LOGIN_PROBLEM = "error.account.login.exists";


    public LoginAlreadyExistsException() {
        super();
    }

    public LoginAlreadyExistsException(String message) {
        super(KEY_ACCOUNT_LOGIN_PROBLEM);
    }

    public LoginAlreadyExistsException(Throwable cause) {
        super(KEY_ACCOUNT_LOGIN_PROBLEM, cause);
    }

}
