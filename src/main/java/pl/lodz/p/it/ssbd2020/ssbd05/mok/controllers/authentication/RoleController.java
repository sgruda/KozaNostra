package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers.authentication;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class RoleController implements Serializable {

    public boolean userHasRole(String role) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return externalContext.isUserInRole(role);
    }

    public String getUserRole() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        if (externalContext.isUserInRole("ADMIN")) {
            return "Admin";
        } else if (externalContext.isUserInRole("MANAGER")) {
            return "Manager";
        } else if (externalContext.isUserInRole("CLIENT")) {
            return "Client";
        } else {
            return "Guest";
        }
    }
}
