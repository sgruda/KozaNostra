package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ConfirmAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;

import javax.annotation.security.PermitAll;
import javax.ejb.*;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Slf4j
@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Local
public class ConfirmAccountEndpoint implements Serializable, ConfirmAccountEndpointLocal {

    @Inject
    private AccountManager accountManager;
    private Account account;

    @PermitAll
    public AccountDTO getAccountByToken(String token) throws AppBaseException {
        account = accountManager.findByToken(token);
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @PermitAll
    public void confirmAccount() throws AppBaseException {
        int callCounter = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("numberOfTransactionRepeat"));
        boolean rollback;
        do {
            try {
                accountManager.confirmAccount(account);
                rollback = accountManager.isLastTransactionRollback();
                callCounter--;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter > 0);
        if (!rollback) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendConfirmedAccountEmail(account.getEmail());
        }
        if (callCounter == 0 && rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
}
