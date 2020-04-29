package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.LastLoginEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

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
    @Inject
    private LastLoginController lastLoginController;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    private String originalUrl;

    @Inject
    private LastLoginEndpoint lastLoginEndpoint;
    @Getter
    private AccountDTO account;

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
        this.account = lastLoginEndpoint.findByLogin(username);
        if(this.account.isActive() && this.account.isConfirmed()){

        //TODO A co z wyjatkiem? jak nie znajdzie? Jakis catch by sie przydal
            if(null != account.getLastSuccessfulAuth())
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastSuccesfullAuthDate", account.getLastSuccessfulAuth());
            if(null != account.getLastFailedAuth())
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastFailedAuthDate", account.getLastFailedAuth());
            lastLoginController.startConversation(account, lastLoginEndpoint.getFailedAttemptNumberFromProperties());
            try {
                request.login(username, password);
                roleController.setSelectedRole(roleController.getAllUserRoles()[0]);
                externalContext.redirect(originalUrl);
                externalContext.getSessionMap().put("printLastLoginInfo", true);
                lastLoginController.updateLastSuccesfullAuthDate();
            } catch (ServletException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect credentials", null));
                lastLoginController.updateLastFailedAuthDate();
                try {
                    lastLoginController.checkFailedAuthCounter();
                } catch (Exception ex) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundles.getTranslatedText("page.login.account.lock"), null));
                }
            }
            lastLoginController.updateLastAuthIP();
            this.lastLoginEndpoint.edit(lastLoginController.endConversation());
        }else if(!this.account.isActive()  && !this.account.isConfirmed()) {
            updateAuthFailureInfo();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is not confirmed", null));
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is not active", null));
        }else if(!this.account.isActive()  && this.account.isConfirmed()) {
            updateAuthFailureInfo();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is not active", null));
        }else if(this.account.isActive()  && !this.account.isConfirmed()){
            updateAuthFailureInfo();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is not confirmed", null));
        }
    }

    public void informAboutLastAuthentication() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        boolean printLastLoginInfo = (boolean) externalContext.getSessionMap().getOrDefault("printLastLoginInfo", false);
        if (printLastLoginInfo) {
            if(null != externalContext.getSessionMap().get("lastSuccesfullAuthDate"))
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Last correct authentication",  externalContext.getSessionMap().get("lastSuccesfullAuthDate").toString() ) );
            if(null != externalContext.getSessionMap().get("lastFailedAuthDate"))
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Last incorrect authentication",  externalContext.getSessionMap().get("lastFailedAuthDate").toString() ) );
            externalContext.getSessionMap().remove("printLastLoginInfo");
            externalContext.getSessionMap().remove("lastSuccesfullAuthDate");
            externalContext.getSessionMap().remove("lastFailedAuthDate");
        }
    }

    public void updateAuthFailureInfo(){
        lastLoginController.startConversation(account, lastLoginEndpoint.getFailedAttemptNumberFromProperties());
        lastLoginController.updateLastFailedAuthDate();
        lastLoginController.updateLastAuthIP();
        this.lastLoginEndpoint.edit(lastLoginController.endConversation());
    }
}
