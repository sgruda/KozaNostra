package pl.lodz.p.it.ssbd2020.ssbd05.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class AccountManager {
    @Inject
    private AccountFacade accountFacade;

    //TODO: stworzenie własnych wyjątków i obsługa ich

    public Account findById(Long id) {
        if(accountFacade.find(id).isPresent())
            return accountFacade.find(id).get();
        else throw new IllegalArgumentException("Nie ma konta o takim ID");
    }

    public Account findByLogin(String login) {
        if(accountFacade.findByLogin(login).isPresent())
            return accountFacade.findByLogin(login).get();
        else throw new IllegalArgumentException("Konto o podanym loginie nie istnieje");
    }

    public void edit(Account account) {
        accountFacade.edit(account);
    }

    public Collection<Account> getAllAccounts() {
        if(accountFacade.getAllAccounts().isPresent())
            return accountFacade.getAllAccounts().get();
        else throw new IllegalArgumentException("Nie ma żadnych kont w bazie");
    }
}