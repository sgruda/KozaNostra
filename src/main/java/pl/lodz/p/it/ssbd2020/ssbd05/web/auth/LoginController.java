package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
@Named
@ViewScoped
public class LoginController implements Serializable {

    @Inject
    private RoleController roleController;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    private String originalUrl;

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
    }

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.login(username, password);
            roleController.setSelectedRole(roleController.getAllUserRoles()[0]);
            externalContext.redirect(originalUrl);
            externalContext.getSessionMap().put("printLastLoginInfo", true);

        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Incorrect credentials"));
        }
    }
    public void informAboutLastAuthentication() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        boolean printLastLoginInfo = (boolean) externalContext.getSessionMap().getOrDefault("printLastLoginInfo", false);
        if (printLastLoginInfo) {
            context.addMessage(null, new FacesMessage("Successful",  "Last correct authentication..." ) );
            context.addMessage(null, new FacesMessage("Successful",  "Last incorrect authentication..." ) );
            externalContext.getSessionMap().remove("printLastLoginInfo");
        }
    }
}
