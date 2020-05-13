package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class ExceededTransactionRetriesException extends AppBaseException {
    static final public String KEY_TRANSACTION_RETIRES_EXCEEDED= "error.transaction.number.exceeded";


    public ExceededTransactionRetriesException(){
        super(KEY_TRANSACTION_RETIRES_EXCEEDED);
    }

    public ExceededTransactionRetriesException(String msg){
        super(msg);
    }

    public ExceededTransactionRetriesException(Throwable ex){
        super(KEY_TRANSACTION_RETIRES_EXCEEDED,ex);
    }
}
