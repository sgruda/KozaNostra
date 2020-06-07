package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.EditAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Kontroler odpowiedzialny za zmianę własnego hasła
 */
@Log
@Named
@ViewScoped
public class ChangePasswordAccountController implements Serializable {

    @Inject
    private EditAccountEndpointLocal editAccountEndpointLocal;

    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String newPassword;
    @Getter
    @Setter
    private String newConfirmPassword;
    @Getter
    @Setter
    private AccountDTO accountDTO;

    /**
     * Metoda wczytujące dane własnego konta do edycji w postaci obiektu typu AccountDTO
     */
    @PostConstruct
    public void init() {
        try {
            accountDTO = editAccountEndpointLocal.findByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        } catch (AppBaseException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
    }

    /**
     * Metoda dokonująca sprawdzenia poprawności formularza
     * i edycji obiektu typu AccountDTO polegającej na ustawieniu w nim nowego hasła
     */
    public void changePassword() {
        if(HashGenerator.sha256(password).equals(accountDTO.getPassword())){
            try {
                accountDTO.setPassword(newPassword);
                editAccountEndpointLocal.changePassword(accountDTO);
                ResourceBundles.emitMessageWithFlash(null,"page.changepassword.message");
            }catch(AppOptimisticLockException ex){
                ResourceBundles.emitErrorMessageWithFlash(null,"error.account.optimisticlock");
                log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            }catch(AccountPasswordAlreadyUsedException ex){
                log.severe(ex.getMessage() + ", " + LocalDateTime.now());
                ResourceBundles.emitErrorMessageWithFlash(null,ex.getMessage());
            }catch (ValidationException e) {
                log.severe(e.getMessage() + ", " + LocalDateTime.now());
                ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
            }catch(AppBaseException ex){
                log.severe(ex.getMessage() + ", " + LocalDateTime.now());
                ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            }
        }else{
            ResourceBundles.emitErrorMessageWithFlash(null,"page.changepassword.wrong.current.password");
        }
        return;
    }

    /**
     * Metoda przenosząca na stronę ze szczegółami własnego konta po pomyślnej zmianie hasła
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goBack() {
        return "accountDetails";
    }
}
