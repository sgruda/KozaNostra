package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ConfirmAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Slf4j
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ConfirmAccountEndpoint implements Serializable, ConfirmAccountEndpointLocal {

    @Inject
    private AccountManager accountManager;
    private Account account;

    @PermitAll
    public AccountDTO getAccountByToken(String token) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                account = accountManager.findByToken(token);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @PermitAll
    public void confirmAccount() throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.confirmAccount(account);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (!rollback) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendConfirmedAccountEmail(account.getEmail());
        }
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
}
