package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.ForgotPasswordTokenDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ResetPasswordEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Slf4j
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

    @Getter
    @Setter
    private String url = "";
    private String token = "";
    private ForgotPasswordTokenDTO forgotPasswordTokenDTO;

    public String goBack() {
        return "/login/login.xhtml?faces-redirect=true";
    }

    public String moveToResetPassword() {
        return "resetPassword";
    }

    public String resetPassword() {
        try {
            resetPasswordEndpoint.findByMail(mail);
            resetPasswordEndpoint.resetPassword(mail);
            ResourceBundles.emitMessageWithFlash(null, "messages.resetpassword.mail");
        } catch (AccountNotFoundException e) {
            log.warn(e.getMessage() + "Wrong email provided during resetting password");
            ResourceBundles.emitMessageWithFlash(null, "messages.resetpassword.mail");
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
        return "home";
    }

    public String changePassword() {
        if(url.contains("token="))
            token = url.substring(url.indexOf("token=") + 6);

        try {
            forgotPasswordTokenDTO = resetPasswordEndpoint.findByHash(token);
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }

        return "home";
    }
}
