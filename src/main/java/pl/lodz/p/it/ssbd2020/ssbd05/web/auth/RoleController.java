package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Named
@SessionScoped
public class RoleController implements Serializable {

    @Getter @Setter
    private String selectedRole = "";
    private Properties userRolesProperties;
    @Getter @Setter
    private Collection<String> roleList = new ArrayList<>();

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
        if (context.isUserInRole(userRolesProperties.getProperty("roleClient"))) {
            roles.add(userRolesProperties.getProperty("roleClient"));
        }
        if (context.isUserInRole(userRolesProperties.getProperty("roleManager"))) {
            roles.add(userRolesProperties.getProperty("roleManager"));
        }
        if (context.isUserInRole(userRolesProperties.getProperty("roleAdmin"))) {
            roles.add(userRolesProperties.getProperty("roleAdmin"));
        }
        this.roleList.addAll(roles);
        return roles.toArray(new String[0]);
    }

    public boolean isInClientRole(){
        boolean inRole = false;
        for (String role: roleList){
                if(role.equals(userRolesProperties.getProperty("roleClient"))){
                    inRole = true;
                }
        }
        return inRole;
    }

    public boolean isInManagerRole(){
        boolean inRole = false;
        for (String role: roleList){
            if(role.equals(userRolesProperties.getProperty("roleManager"))){
                inRole = true;
            }
        }
        return inRole;
    }

    public boolean isInAdminRole(){
        boolean inRole = false;
        for (String role: roleList){
            if(role.equals(userRolesProperties.getProperty("roleAdmin"))){
                inRole = true;
            }
        }
        return inRole;
    }

    public int getAllUserRolesLength() {
        return getAllUserRoles().length;
    }

    public String getThemeForRole() {
        if(this.selectedRole.equalsIgnoreCase(userRolesProperties.getProperty("roleAdmin")))
            return userRolesProperties.getProperty("roleAdminThema");
        else if(this.selectedRole.equalsIgnoreCase(userRolesProperties.getProperty("roleManager")))
            return userRolesProperties.getProperty("roleManagerThema");
        else if(this.selectedRole.equalsIgnoreCase(userRolesProperties.getProperty("roleClient")))
            return userRolesProperties.getProperty("roleClientThema");
        else return userRolesProperties.getProperty("defaultThema");
    }
    
    public String getHeaderColorForRole() {
        if(this.selectedRole.equalsIgnoreCase(userRolesProperties.getProperty("roleAdmin")))
            return userRolesProperties.getProperty("roleAdminColor");
        else if(this.selectedRole.equalsIgnoreCase(userRolesProperties.getProperty("roleManager")))
            return userRolesProperties.getProperty("roleManagerColor");
        else if(this.selectedRole.equalsIgnoreCase(userRolesProperties.getProperty("roleClient")))
            return userRolesProperties.getProperty("roleClientColor");
        else return userRolesProperties.getProperty("defaultColor");
    }
    public String getRoleAdmin() {
        return userRolesProperties.getProperty("roleAdmin");
    }
    public String getRoleManager() {
        return userRolesProperties.getProperty("roleManager");
    }
    public String getRoleClient() {
        return userRolesProperties.getProperty("roleClient");
    }

    @PostConstruct
    private void loadProperties() {
        this.userRolesProperties = new Properties();
        try {
            this.userRolesProperties = ResourceBundles.loadProperties("config.user_roles.properties");
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessage(null, "error.simple");
        }
    }
}
