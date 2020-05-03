package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;


public class AccountException extends AppBaseException {
    static final public String KEY_ACCOUNT_LOGIN_PROBLEM = "error.account.login.exists";
    static final public String KEY_ACCOUNT_EMAIL_PROBLEM = "error.account.email.exists";
    static final public String KEY_ACCOUNT_NOT_FOUND = "error.account.not.found";


    private Account account;

    public AccountException(String msg, Throwable ex, Account account) {
        super(msg,ex);
        this.account = account;
    }

    public static AccountException emailExistsException(Throwable ex, Account account) {
        return new AccountException(KEY_ACCOUNT_EMAIL_PROBLEM, ex, account);
    }

    static public AccountException loginExistsException(Throwable ex, Account account) {
        return new AccountException(KEY_ACCOUNT_LOGIN_PROBLEM, ex, account);
    }

    static public AccountException accountNotFoundException(Throwable ex,Account account){
        return new AccountException(KEY_ACCOUNT_NOT_FOUND,ex,account);
    }
}
