package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ResetPasswordEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ResetPasswordController {

    @Inject
    ResetPasswordEndpointLocal resetPasswordEndpoint;

    @Getter
    @Setter
    private String mail;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String confirmPassword;

    public String goBack() {
        return "/login/login.xhtml?faces-redirect=true";
    }

    public String moveToResetPassword() {
        return "resetPassword";
    }

    public String resetPassword() {
        try {
            resetPasswordEndpoint.resetPassword(mail);
            ResourceBundles.emitMessageWithFlash(null, "messages.resetpassword.mail");
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
        return "home";
    }

    public String changePassword() {

        return "home";
    }
}
