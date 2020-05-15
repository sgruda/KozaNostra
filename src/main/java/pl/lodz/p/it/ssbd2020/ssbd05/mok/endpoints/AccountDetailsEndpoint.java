package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Slf4j
@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class AccountDetailsEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;
    @Resource
    private SessionContext sessionContext;

    private Account account;

    @RolesAllowed("getOtherAccount")
    public AccountDTO getAccount(String login) throws ExceededTransactionRetriesException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                this.account = accountManager.findByLogin(login);
                rollback = accountManager.isLastTransactionRollback();
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

    @RolesAllowed("getOwnAccount")
    public AccountDTO getOwnAccount() throws ExceededTransactionRetriesException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                String login = sessionContext.getCallerPrincipal().getName();
                this.account = accountManager.findByLogin(login);
                rollback = accountManager.isLastTransactionRollback();
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
            log.warn("EJBTransactionRolledBack");
            rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (callCounter == 0 && rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }
}
