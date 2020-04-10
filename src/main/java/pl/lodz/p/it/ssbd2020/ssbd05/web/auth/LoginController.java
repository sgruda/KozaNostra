package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    @Inject
    private LastLoginController lastLoginController;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    private String originalUrl;

    @EJB
    private AccountFacade accountFacade; //docelowo AccountService
    @Getter
    private Account account;

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

        this.account = accountFacade.findByLogin(username);
        //TODO A co z wyjatkiem? jak nie znajdzie? Jakis catch by sie przydal
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastSuccesfullAuthDate", account.getLastSuccessfulAuth());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastFailedAuthDate", account.getLastFailedAuth());
        lastLoginController.startConversation(account);
        try {
            request.login(username, password);
            roleController.setSelectedRole(roleController.getAllUserRoles()[0]);
            externalContext.redirect(originalUrl);
            externalContext.getSessionMap().put("printLastLoginInfo", true);
            lastLoginController.updateLastSuccesfullAuthDate();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Incorrect credentials"));
            lastLoginController.updateLastFailedAuthDate();
        }
        lastLoginController.updateLastAuthIP();
        this.accountFacade.edit(lastLoginController.endConversation());
    }
    public void informAboutLastAuthentication() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        boolean printLastLoginInfo = (boolean) externalContext.getSessionMap().getOrDefault("printLastLoginInfo", false);
        if (printLastLoginInfo) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Last correct authentication",  externalContext.getSessionMap().getOrDefault("lastSuccesfullAuthDate", "Default").toString() ) );
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Last incorrect authentication",  externalContext.getSessionMap().getOrDefault("lastFailedAuthDate", "Default").toString() ) );
            externalContext.getSessionMap().remove("printLastLoginInfo");
            externalContext.getSessionMap().remove("lastSuccesfullAuthDate");
            externalContext.getSessionMap().remove("lastFailedAuthDate");
        }
    }
}
