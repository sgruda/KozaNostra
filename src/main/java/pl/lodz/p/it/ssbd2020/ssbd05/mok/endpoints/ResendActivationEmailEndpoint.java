package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ResendActivationEmailEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;


/**
 * Punkt dostępowy implementujący interfejs ResendActivationEmailEndpointLocal
 * pośredniczący przy powtórnym wysyłaniu maila z linkiem aktywacyjnym
 */
@Log
@Stateful
@Interceptors(TrackerInterceptor.class)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ResendActivationEmailEndpoint implements ResendActivationEmailEndpointLocal {

    @Inject
    private AccountManager accountManager;
    private Account account;

    @Override
    @RolesAllowed("resendEmail")
    public void resendEmail(String login) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                account = accountManager.findByLogin(login);
                rollback = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException e) {
                log.warning("EJBTransactionRolledBack");
                rollback = true;
            }
            if(callCounter > 0)
                log.info("Transaction with ID " + accountManager.getTransactionId() + " is being repeated for " + callCounter + " time");
            callCounter++;
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        } else {
            if(!account.isConfirmed()) {
                EmailSender emailSender = new EmailSender();
                emailSender.sendRegistrationEmail(account.getEmail(), account.getVeryficationToken());
            } else throw new AccountAlreadyConfirmedException(ResourceBundles.getTranslatedText("error.account.confirmed"));
        }
    }
}
