package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class AppOptimisticLockException extends AppBaseException {
    static final public String KEY_DATABASE_OPTIMISTIC_LOCK = "error.database.optimistic.lock";

    public AppOptimisticLockException(){ super(KEY_DATABASE_OPTIMISTIC_LOCK);}

    public AppOptimisticLockException(String msg){
        super(msg);
    }

    public AppOptimisticLockException(Throwable ex){
        super(ex);
    }
}