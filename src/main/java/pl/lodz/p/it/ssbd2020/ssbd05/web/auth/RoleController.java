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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

@Named
@Log
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

    public void setCurrentRole(String role) {
        this.setSelectedRole(role);
        logRoleChange(role);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        } catch (IOException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, "error.default");
        }
    }

    private void logRoleChange(String role){
        StringBuilder sb = new StringBuilder();
        sb.append("User: ");
        sb.append(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal());
        sb.append(" IP: ");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if(remoteAddr != null){
            remoteAddr = remoteAddr.replaceFirst(",.*","");
        }
        else{
            remoteAddr = request.getRemoteAddr();
        }
        sb.append(remoteAddr);
        sb.append(" changed role to: ");
        sb.append(role);
        log.info(sb.toString());
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
        return roleList.contains(userRolesProperties.getProperty("roleClient"));
    }

    public boolean isInManagerRole(){
        return roleList.contains(userRolesProperties.getProperty("roleManager"));
    }

    public boolean isInAdminRole(){
        return roleList.contains(userRolesProperties.getProperty("roleAdmin"));
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
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }
}
