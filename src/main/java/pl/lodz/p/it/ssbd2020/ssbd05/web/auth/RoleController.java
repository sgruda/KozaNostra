package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Getter;
import lombok.Setter;

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
    private String selectedRole = "";

    public boolean isSelectedRole(String role) {
        return selectedRole.equalsIgnoreCase(role);
    }

    public void setCurrentRole(String role) throws IOException {
        this.setSelectedRole(role);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }

    public String[] getAllUserRoles() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
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

    public int getAllUserRolesLength() {
        return getAllUserRoles().length;
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
    
        public String getHeaderColorForRole() {
        if(this.selectedRole.equalsIgnoreCase("admin"))
            return "#102b3d;";
        else if(this.selectedRole.equalsIgnoreCase("manager"))
            return "#1f567a;";
        else if(this.selectedRole.equalsIgnoreCase("client"))
            return "#338fcc;";
        else return "#8fc1e3;";
    }
}
