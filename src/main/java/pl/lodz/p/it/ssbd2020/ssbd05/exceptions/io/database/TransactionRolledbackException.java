package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class TransactionRolledbackException extends AppBaseException {

    static final public String KEY_TRANSACTION_ROLLEDBACK= "error.transaction.rolledback";


    public TransactionRolledbackException(){
        super(KEY_TRANSACTION_ROLLEDBACK);
    }

    public TransactionRolledbackException(String msg){
        super(msg);
    }

    public TransactionRolledbackException(Throwable ex){
        super(KEY_TRANSACTION_ROLLEDBACK,ex);
    }

}
