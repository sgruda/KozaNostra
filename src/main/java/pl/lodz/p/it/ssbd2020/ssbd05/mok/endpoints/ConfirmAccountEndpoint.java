package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountAlreadyConfirmedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ConfirmAccountEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;
    private Account account;

    public AccountDTO getAccountByToken(String token) throws AppBaseException {
        account = accountManager.findByToken(token);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setVeryficationToken(account.getVeryficationToken());
        return accountDTO;
    }

    public void confirmAccount() throws AccountAlreadyConfirmedException {
        if(!account.isConfirmed()) {
            account.setConfirmed(true);
            account.setVeryficationToken("used");
            accountManager.edit(account);
        }
        else throw new AccountAlreadyConfirmedException(ResourceBundles.getTranslatedText("error.account.confirmed"));
    }
}
