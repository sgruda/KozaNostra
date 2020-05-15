package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class ListAccountsEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;

    @RolesAllowed("listAccounts")
    public Collection<AccountDTO> getAllAccounts() throws ExceededTransactionRetriesException {
        Collection<AccountDTO> list = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                list = AccountMapper.INSTANCE.toAccountDTOCollection(accountManager.getAllAccounts());
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
        return list;
    }

    @RolesAllowed("filterAccounts")
    public Collection<AccountDTO> filterAccounts (String accountFilter) throws ExceededTransactionRetriesException {
        Collection<AccountDTO> list = new ArrayList<>();
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                list = AccountMapper.INSTANCE.toAccountDTOCollection(accountManager.filterAccounts(accountFilter));
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
        return list;
    }
}
