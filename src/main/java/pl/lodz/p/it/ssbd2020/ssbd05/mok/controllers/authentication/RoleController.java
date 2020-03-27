package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class RoleController implements Serializable {

    @Getter @Setter
    private String selectedRole;

    public boolean isUserInOneOfRoles(String... roles) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        for (String role : roles) {
            if (externalContext.isUserInRole(role)) {
                return true;
            }
        }
        return false;
    }

    public String[] getAllUserRoles() {
        List<String> roles = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        if (externalContext.isUserInRole("CLIENT")) {
            roles.add("Client");
        }
        if (externalContext.isUserInRole("MANAGER")) {
            roles.add("Manager");
        }
        if (externalContext.isUserInRole("ADMIN")) {
            roles.add("Admin");
        }
        if (roles.isEmpty()) {
            roles.add("Guest");
        }
        return roles.toArray(new String[0]);
    }

    public void redirectByRole() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        switch (selectedRole) {
            case "Client":
                externalContext.redirect("client/clientPage.xhtml");
                break;
            case "Manager":
                externalContext.redirect("manager/managerPage.xhtml");
                break;
            case "Admin":
                externalContext.redirect("admin/adminPage.xhtml");
                break;
            default:
                externalContext.redirect("index.xhtml");
                break;
        }
    }

    @PostConstruct
    public void setPrimaryRole() {
        selectedRole = getAllUserRoles()[0];
    }
}
