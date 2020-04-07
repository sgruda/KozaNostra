package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
@Named
@RequestScoped
public class LoginController implements Serializable {

    @Inject
    private RoleController roleController;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    private String originalUrl;
    @Getter
    private boolean printLastLoginInfo;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalUrl = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalUrl == null) {
            originalUrl = externalContext.getRequestContextPath() + "/index.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalUrl += "?" + originalQuery;
            }
        }
        printLastLoginInfo = false;
    }

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(username, password);
            roleController.setSelectedRole(roleController.getAllUserRoles()[0]);
            externalContext.redirect(originalUrl);
            printLastLoginInfo = true;

        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Incorrect credentials"));
        }
    }
}
