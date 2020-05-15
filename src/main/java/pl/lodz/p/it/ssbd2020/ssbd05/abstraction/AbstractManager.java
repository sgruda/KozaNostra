package pl.lodz.p.it.ssbd2020.ssbd05.abstraction;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.EJBException;
import java.rmi.RemoteException;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
abstract public class AbstractManager  {

    @Getter
    private boolean lastTransactionRollback;
    private String transactionId;

    public void afterBegin() throws EJBException, RemoteException {
        transactionId = Long.toString(System.currentTimeMillis()) + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        log.info("Transaction with ID: " + transactionId + " has been started");
    }

    public void beforeCompletion() throws EJBException, RemoteException {
        log.info("Transaction with ID: " + transactionId + " before completion");
    }

    public void afterCompletion(boolean committed) throws EJBException, RemoteException {
        lastTransactionRollback = !committed;
        log.info("Transaction with ID: " + transactionId + " has been completed by: " + (committed?"commit":"rollback"));
    }
}
