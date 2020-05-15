package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.PropertiesLoadingException;
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

    public void login() throws AppBaseException, IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        this.account = lastLoginEndpoint.findByLogin(username);
        if(this.account.isActive() && this.account.isConfirmed()){

        //TODO A co z wyjatkiem? jak nie znajdzie? Jakis catch by sie przydal
            try {
                lastLoginController.startConversation(account, lastLoginEndpoint.getFailedAttemptNumberFromProperties());
                request.login(username, password);
                roleController.setSelectedRole(roleController.getAllUserRoles()[0]);
                this.emitMessegesAfterLogin();
                externalContext.redirect(originalUrl);
                lastLoginController.updateLastSuccesfullAuthDate();
            } catch (ServletException e) {
                ResourceBundles.emitErrorMessage(null,"page.login.incorrectcredentials");
                lastLoginController.updateLastFailedAuthDate();
                lastLoginController.checkFailedAuthCounter();
            } catch(PropertiesLoadingException ex) {
                ResourceBundles.emitErrorMessageWithFlash(null, ResourceBundles.getTranslatedText("error.simple"));
                Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, ex.getClass().toString(), ex);
            }
            Properties properties = new Properties();
            try {
                properties = ResourceBundles.loadProperties("config.user_roles.properties");
            } catch (AppBaseException e) {
                ResourceBundles.emitErrorMessage(null, "error.simple");
            }
            if(account.getAccessLevelCollection().contains( properties.getProperty("roleAdmin"))) {
                EmailSender emailSender = new EmailSender();
                emailSender.sendAuthorizedAdminEmail(account.getEmail(), LocalDateTime.now(), lastLoginController.getIP());
            }

            lastLoginController.updateLastAuthIP();
            try {
                this.lastLoginEndpoint.edit(lastLoginController.endConversation());
            } catch (AppBaseException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else if(!this.account.isActive()  && !this.account.isConfirmed()) {
            updateAuthFailureInfo();
            ResourceBundles.emitErrorMessage(null,"page.login.user.notconfirmed");
            ResourceBundles.emitErrorMessage(null,"page.login.user.notactive");
        }else if(!this.account.isActive()  && this.account.isConfirmed()) {
            updateAuthFailureInfo();
            ResourceBundles.emitErrorMessage(null,"page.login.user.notactive");
        }else if(this.account.isActive()  && !this.account.isConfirmed()){
            updateAuthFailureInfo();
            ResourceBundles.emitErrorMessage(null,"page.login.user.notconfirmed");
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
