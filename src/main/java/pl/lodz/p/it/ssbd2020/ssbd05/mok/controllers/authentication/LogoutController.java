package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers.authentication;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@RequestScoped
@Named
public class LogoutController {

    @Inject
    RoleController roleController;

    public void logout() throws IOException {
        roleController.setSelectedRole(null);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }
}
