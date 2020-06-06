package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;

/**
 * Kontroler odpowiedzialny za wylogowywanie użytkownika
 */
@RequestScoped
@Named
@Log
public class LogoutController {

    @Inject
    private RoleController roleController;

    /**
     * Metoda odpowiedzialna za kończenie sesji obecnie zalogowanego użytkownika
     */
    public void logout() {
        roleController.setSelectedRole(null);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        log.info("["+LocalDateTime.now()+"] User: "+ externalContext.getUserPrincipal().getName() + " session invalidated");

        externalContext.invalidateSession();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        } catch (IOException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, "error.default");
        }
    }
}
