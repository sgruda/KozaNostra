package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ListAccountsEndpointLocal;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Kontroler odpowiedzialny za wyświetlenie wszystkich kont w systemie dla poziomu dostępu Administrator
 */
@Log
@Named
@ViewScoped
public class ListAccountsController implements Serializable {

    @Inject
    private ListAccountsEndpointLocal listAccountsEndpointLocal;

    @Getter
    @Setter
    private List<AccountDTO> accounts;

    @Getter
    @Setter
    private String accountFilter;

    /**
     * Metoda wykonywana po stworzeniu instancji ListAccountController. Pobiera wszystkie konta z bazy
     */
    @PostConstruct
    public void init() {
        try {
            accounts = (List<AccountDTO>) listAccountsEndpointLocal.getAllAccounts();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    /**
     * Metoda służąca do filtrowania po liście kont zgodnie z przekazanym filtrem
     */
    public void filterAccounts(){
        try {
            accounts = (List<AccountDTO>) listAccountsEndpointLocal.filterAccounts(accountFilter);
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    /**
     * Metoda służąca do pobrania konta z bazy oraz przekierowania do strony ze szczegółami
     *
     * @param login login użytkownika
     * @return string
     */
    public String selectAccount(String login) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedLogin", login);
        return "accountDetails";
    }
}
