package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.PropertiesLoadingException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.LastLoginEndpoint;
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
@Slf4j
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

    public void login()  {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            this.account = lastLoginEndpoint.findByLogin(username);


        if(this.account.isActive() && this.account.isConfirmed()){

            //TODO A co z wyjatkiem? jak nie znajdzie? Jakis catch by sie przydal
            try {
                try {
                    lastLoginController.startConversation(account, lastLoginEndpoint.getFailedAttemptNumberFromProperties());
                } catch (AppBaseException appBaseException) {
                    log.warn(appBaseException.getClass().toString() + " " + appBaseException.getMessage());
                }
                request.login(username, password);
                roleController.setSelectedRole(roleController.getAllUserRoles()[0]);
                this.emitMessegesAfterLogin();
                try {
                    externalContext.redirect(originalUrl);
                } catch (IOException e) {
                    log.warn(e.getClass().toString() + " " + e.getMessage());
                    ResourceBundles.emitErrorMessage(null, "page.login.redirect");
                }
                lastLoginController.updateLastSuccesfullAuthDate();
            } catch (ServletException e) {
                ResourceBundles.emitErrorMessage(null, "page.login.incorrectcredentials");
                lastLoginController.updateLastFailedAuthDate();
                try {
                    lastLoginController.checkFailedAuthCounter();
                } catch (AppBaseException appBaseException) {
                    log.warn(appBaseException.getClass().toString() + " " + appBaseException.getMessage());
                }
            }
            Properties properties = new Properties();
            try {
                properties = ResourceBundles.loadProperties("config.user_roles.properties");
            } catch (AppBaseException e) {
                ResourceBundles.emitErrorMessage(null, "error.simple");
            }
            if(account.getAccessLevelCollection().contains( properties.getProperty("roleAdmin"))) {
                EmailSender emailSender = null;
                try {
                    emailSender = new EmailSender();
                } catch (AppBaseException appBaseException) {
                    log.warn(appBaseException.getClass().toString() + " " + appBaseException.getMessage());
                }
                emailSender.sendAuthorizedAdminEmail(account.getEmail(), LocalDateTime.now(), lastLoginController.getIP());
            }

            lastLoginController.updateLastAuthIP();
            try {
                this.lastLoginEndpoint.edit(lastLoginController.endConversation());
            } catch (AppBaseException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }  else if(!this.account.isActive() || !this.account.isConfirmed()) {
            try {
                updateAuthFailureInfo();
            } catch (AppBaseException appBaseException) {
                log.warn(appBaseException.getClass().toString() + " " + appBaseException.getMessage());
            }
            ResourceBundles.emitErrorMessageWithDetails(null,"page.login.account.notconfirmed.or.notactive", "page.login.contactadmin");
        }
    } catch (AccountNotFoundException e) {
            ResourceBundles.emitErrorMessage(null, "page.login.incorrectcredentials");
        }
    }

    private void updateAuthFailureInfo() throws AppBaseException{
        try {
            lastLoginController.startConversation(account, lastLoginEndpoint.getFailedAttemptNumberFromProperties());
        } catch(PropertiesLoadingException ex) {
            ResourceBundles.emitErrorMessageWithFlash(null, ResourceBundles.getTranslatedText("error.simple"));
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, ex.getClass().toString(), ex);
        }
        lastLoginController.updateLastFailedAuthDate();
        lastLoginController.updateLastAuthIP();
        try {
            this.lastLoginEndpoint.edit(lastLoginController.endConversation());
        } catch (AppBaseException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
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
