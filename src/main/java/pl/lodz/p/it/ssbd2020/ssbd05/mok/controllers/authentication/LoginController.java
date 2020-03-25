package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers.authentication;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.model.User;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.services.users.UserService;


import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


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
            //if (user != null && user.isActive()) {
                if (request.isUserInRole("ADMIN")) {
                    externalContext.getSessionMap().put("role", "Admin");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/mok/admin/adminPage.xhtml");
//                    PrimeFaces.current().ajax().addCallbackParam("loggedIn", true);
                } else if (request.isUserInRole("MANAGER")) {
                    externalContext.getSessionMap().put("role", "Manager");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/mok/manager/managerPage.xhtml");
//                    PrimeFaces.current().ajax().addCallbackParam("loggedIn", true);
                } else if (request.isUserInRole("CLIENT")) {
                    externalContext.getSessionMap().put("role", "Client");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/mok/client/clientPage.xhtml");
//                    PrimeFaces.current().ajax().addCallbackParam("loggedIn", true);
                } else {
//                    externalContext.redirect(externalContext.getRequestContextPath() + "/mok/login/login.xhtml?error=true");
                    externalContext.redirect(externalContext.getRequestContextPath() + "/mok/login/error.xhtml");
                }
            //}
        } catch (ServletException e) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/mok/login/login.xhtml?error=true");
        }
    }

//    @Inject
//    private SecurityContext securityContext;
//
//    @Inject
//    private ExternalContext externalContext;
//
//    @Inject
//    private FacesContext facesContext;
//    private String userType;
//public void login() throws IOException {
//
//    switch (continueAuthentication()) {
//        case SEND_CONTINUE:
//            facesContext.responseComplete();
//            break;
//        case SEND_FAILURE:
//            Logger.getLogger(LoginController.class.getName()).log(Level.WARNING, "Custom logger message : Login failed");
//            facesContext.addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
//
//            break;
//        case SUCCESS:
//            Logger.getLogger(LoginController.class.getName()).log(Level.INFO, "Custom logger message : Login succeeded");
//            facesContext.addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Login succeed", null));
//            User user = userService.getUser(username);
//            if(user instanceof User){
//                userType = "admin";
//            }
//            switch (userType) {
//                case ("admin"):
//                case("ADMIN"):
//                    externalContext.redirect(externalContext.getRequestContextPath() + "/admin/adminPage.xhtml?faces-redirect=true");
//                    break;
//                case "client":
//                case "CLIENT":
//                    externalContext.redirect(externalContext.getRequestContextPath() + "/client/clientPage.xhtml");
//                    break;
//                case "manager":
//                case "MANAGER":
//                    externalContext.redirect(externalContext.getRequestContextPath() + "/manager/managerPage.xhtml");
//                    break;
//            }
//            break;
//        case NOT_DONE:
//    }
//}

    private AuthenticationStatus continueAuthentication() {
//        return securityContext.authenticate(
//                (HttpServletRequest) externalContext.getRequest(),
//                (HttpServletResponse) externalContext.getResponse(),
//                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(username, password))
//        );
        return null;
    }
    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }

}
