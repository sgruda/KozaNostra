package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.LastLoginEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
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
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log
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
    private LastLoginEndpointLocal lastLoginEndpointLocal;
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

    public void login()  {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            this.account = lastLoginEndpointLocal.findByLogin(username);
             if(this.account.isActive() && this.account.isConfirmed()) {
                 try {
                     lastLoginController.startConversation(account, lastLoginEndpointLocal.getFailedAttemptNumberFromProperties());
                     request.login(username, password);
                     roleController.setSelectedRole(roleController.getAllUserRoles()[0]);
                     this.emitMessegesAfterLogin();
                     externalContext.redirect(originalUrl);
                     lastLoginController.updateLastSuccesfullAuthDate();
                 } catch (ServletException e) {
                     log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
                     ResourceBundles.emitErrorMessage(null,"page.login.incorrectcredentials");
                     lastLoginController.updateLastFailedAuthDate();
                     lastLoginController.checkFailedAuthCounter();
                 }
                 Properties properties = ResourceBundles.loadProperties("config.user_roles.properties");
                 if(account.getAccessLevelCollection().contains( properties.getProperty("roleAdmin"))) {
                     EmailSender emailSender;
                     emailSender = new EmailSender();
                     emailSender.sendAuthorizedAdminEmail(account.getEmail(), LocalDateTime.now(), lastLoginController.getIP());
                 }
                 lastLoginController.updateLastAuthIP();
                 this.lastLoginEndpointLocal.edit(lastLoginController.endConversation());
            }  else if(!this.account.isActive() || !this.account.isConfirmed()) {
                 updateAuthFailureInfo();
                 ResourceBundles.emitErrorMessageWithDetails(null,"page.login.account.notconfirmed.or.notactive", "page.login.contactadmin");
            }
        } catch (AccountNotFoundException  e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, "page.login.incorrectcredentials");
        } catch (AppBaseException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, "error.simple");
        } catch (IOException e) {
            log.log(Level.WARNING, e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessage(null, "page.login.redirect");
        }
    }

    private void updateAuthFailureInfo() {
        try {
            lastLoginController.startConversation(account, lastLoginEndpointLocal.getFailedAttemptNumberFromProperties());
            lastLoginController.updateLastFailedAuthDate();
            lastLoginController.updateLastAuthIP();
            this.lastLoginEndpointLocal.edit(lastLoginController.endConversation());
        } catch (AppBaseException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ResourceBundles.getTranslatedText("error.simple"));
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, ex.getClass().toString(), ex);
        }
    }
    private void emitMessegesAfterLogin() {
        if(null != account.getLastSuccessfulAuth()) {
            ResourceBundles.emitDetailedMessageWithFlash(null, "page.login.successful.auth", account.getLastSuccessfulAuth());
        }
        if(null != account.getLastFailedAuth())
            ResourceBundles.emitDetailedErrorWithFlash(null, "page.login.failed.auth", account.getLastFailedAuth());
    }
}