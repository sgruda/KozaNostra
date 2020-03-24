package pl.lodz.p.it.ssbd2020.ssbd05.controllers.authentication;

import lombok.Getter;
import lombok.Setter;


import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable {

    @Inject
    private UserService userService;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            request.login(username, password);
            User user = userService.getUser(username);
            if (user != null && user.isActive()) {
                if (request.isUserInRole("Admin")) {
                    externalContext.getSessionMap().put("role", "Admin");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/admin/adminPage.xhtml");
                } else if (request.isUserInRole("Manager")) {
                    externalContext.getSessionMap().put("role", "Manager");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/manager/managerPageRest.xhtml");
                } else if (request.isUserInRole("Client")) {
                    externalContext.getSessionMap().put("role", "Client");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/client/clientPageRest.xhtml");
                } else {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml?error=true");
                }
            }
        } catch (ServletException e) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml?error=true");
        }
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }
}
