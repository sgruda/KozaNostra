package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;

@RequestScoped
@Named
@Slf4j
public class LogoutController {

    @Inject
    private RoleController roleController;

    public void logout() throws IOException {
        roleController.setSelectedRole(null);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        log.info("["+LocalDateTime.now()+"] User: "+ externalContext.getUserPrincipal().getName() + " session invalidated");

        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }
}
