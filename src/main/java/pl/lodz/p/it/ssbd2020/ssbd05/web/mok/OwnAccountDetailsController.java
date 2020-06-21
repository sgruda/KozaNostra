package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.AccountDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;

/**
 * Kontroler odpowiadający za wyświetlanie szczegółów własnego konta
 */
@Log
@Named
@ViewScoped
public class OwnAccountDetailsController implements Serializable {

    @Inject
    private AccountDetailsEndpointLocal accountDetailsEndpointLocal;
    @Getter
    private AccountDTO account;

    /**
     * Przejdź na stronę ze szczegółami własnego konta
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String selectOwnAccount() {
        return "ownAccountDetails";
    }

    /**
     * Wróć na poprzednią stronę
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goBack() {
        return "goBack";
    }

    /**
     * Metoda wczytująca dane własnego konta
     */
    @PostConstruct
    void init(){
        try {
            this.account = accountDetailsEndpointLocal.getOwnAccount();
        } catch (AppBaseException e) {
            log.log(Level.SEVERE, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, e.getMessage());
        }
    }
}
