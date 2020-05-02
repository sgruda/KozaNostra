package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

@Getter
@Setter
public class AccountExceptions extends AppBaseException {
    static final public String KEY_ACCOUNT_LOGIN_PROBLEM = "error.account.login.exists";
    static final public String KEY_ACCOUNT_EMAIL_PROBLEM = "error.account.email.exists";


    private Account account;

    public AccountExceptions(String msg, Throwable ex, Account account) {
        super(msg,ex);
        this.account = account;
    }

    public static AccountExceptions emailExistsException(Throwable ex, Account account) {
        return new AccountExceptions(KEY_ACCOUNT_EMAIL_PROBLEM, ex, account);
    }

    static public AccountExceptions loginExistsException(Throwable ex, Account account) {
        return new AccountExceptions(KEY_ACCOUNT_LOGIN_PROBLEM, ex, account);
    }
}
