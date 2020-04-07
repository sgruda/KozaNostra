package pl.lodz.p.it.ssbd2020.ssbd05.mok.controllers.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthenticationController implements Serializable {
    @Setter @Getter
    private boolean printLastLoginInfo;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    @Inject
    private LoginController loginController;

    public void login() throws IOException {
        loginController.setUsername(this.username);
        loginController.setPassword(this.password);
        loginController.login();
        this.printLastLoginInfo = loginController.isPrintLastLoginInfo();
        this.username = "";
        this.password = "";
    }
    public void informAboutLastAuthentication() {
        if (printLastLoginInfo) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful",  "Last correct authentication..." ) );
            context.addMessage(null, new FacesMessage("Successful",  "Last incorrect authentication..." ) );
            printLastLoginInfo = false;
        }
    }
}
