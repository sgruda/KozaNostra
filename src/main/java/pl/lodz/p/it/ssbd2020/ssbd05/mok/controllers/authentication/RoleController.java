package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class RoleController implements Serializable {

    @Getter @Setter
    private String selectedRole = "";
    private ExternalContext context;

    private void refreshContext() {
        context = FacesContext.getCurrentInstance().getExternalContext();
    }

    public boolean isSelectedRole(String role) {
        return selectedRole.equalsIgnoreCase(role);
    }

    public boolean isUserInOneOfRoles(String... roles) {
        refreshContext();
        for (String role : roles) {
            if (context.isUserInRole(role)) {
                return true;
            }
        }
        return false;
    }

    public String[] getAllUserRoles() {
        refreshContext();
        List<String> roles = new ArrayList<>();
        if (context.isUserInRole("CLIENT")) {
            roles.add("Client");
        }
        if (context.isUserInRole("MANAGER")) {
            roles.add("Manager");
        }
        if (context.isUserInRole("ADMIN")) {
            roles.add("Admin");
        }
        return roles.toArray(new String[0]);
    }

    public String getThemeForRole() {
        if(this.selectedRole.equalsIgnoreCase("admin"))
            return "luna-green";
        else if(this.selectedRole.equalsIgnoreCase("manager"))
            return "luna-blue";
        else if(this.selectedRole.equalsIgnoreCase("client"))
            return "luna-pink";
        else return "nova-dark";
    }
}
