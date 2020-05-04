package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AppOptimisticLockException extends AppBaseException {
    static final public String KEY_DATABASE_OPTIMISTIC_LOCK = "error.database.optimistic.lock";

    public AppOptimisticLockException(){
    }

    public AppOptimisticLockException(String msg){
        super(msg);
    }

    public AppOptimisticLockException(Throwable ex){
        super(KEY_DATABASE_OPTIMISTIC_LOCK, ex);
    }
}