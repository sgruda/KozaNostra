package pl.lodz.p.it.ssbd2020.ssbd05.abstraction;

import lombok.Getter;
import lombok.extern.java.Log;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Klasa abstrakcyjna, dostarczająca implementacje metod, zawartych w interfejsie SessionSynchronization.
 */
@Log
abstract public class AbstractManager  {

    @Getter
    private boolean lastTransactionRollback;
    @Getter
    private String transactionId;

    @Resource
    private SessionContext sessionContext;

    /**
     * Metoda wywoływana przy rozpoczęciu transakcji.
     *
     * @throws EJBException    Wyjątek systemowy
     * @throws RemoteException Wyjątek systemowy
     */
    public void afterBegin() throws EJBException, RemoteException {
        String login = sessionContext.getCallerPrincipal().getName();
        transactionId = Long.toString(System.currentTimeMillis()) + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        log.info("Transaction with ID: " + transactionId + " has been started, account: "+login);
    }

    /**
     * Metoda wywoływana przed zakończeniem transakcji.
     *
     * @throws EJBException    Wyjątek systemowy
     * @throws RemoteException Wyjątek systemowy
     */
    public void beforeCompletion() throws EJBException, RemoteException {
        String login = sessionContext.getCallerPrincipal().getName();
        log.info("Transaction with ID: " + transactionId + " before completion , account:" +login);
    }

    /**
     * Metoda wywoływana po zakończeniu transakcji.
     *
     * @param committed czy transakcja została zakończona pomyślnie
     * @throws EJBException    wyjątek systemowy
     * @throws RemoteException wyjątek systemowy
     */
    public void afterCompletion(boolean committed) throws EJBException, RemoteException {
        String login = sessionContext.getCallerPrincipal().getName();
        lastTransactionRollback = !committed;
        log.info("Transaction with ID: " + transactionId + " has been completed by: " + (committed?"commit":"rollback")+ " account: "+login);
    }
}
