package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.ForgotPasswordTokenDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ResetPasswordEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;

@Log
@Named
@RequestScoped
public class ResetPasswordController {

    @Inject
    private ResetPasswordEndpointLocal resetPasswordEndpoint;

    @Getter
    @Setter
    private String mail;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String url = "";
    private String token = "";
    private ForgotPasswordTokenDTO forgotPasswordTokenDTO;
    private AccountDTO accountDTO;

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
            log.warning(e.getMessage() + "Wrong email provided during resetting password");
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
            if (forgotPasswordTokenDTO.getExpireDate().isAfter(LocalDateTime.now())) {
                accountDTO = resetPasswordEndpoint.findByLogin(forgotPasswordTokenDTO.getAccount());
                accountDTO.setPassword(password);
                resetPasswordEndpoint.changeResettedPassword(accountDTO);
                ResourceBundles.emitMessageWithFlash(null, "messages.resetpassword.success");
            } else ResourceBundles.emitErrorMessageWithFlash(null, "messages.resetpassword.expired");
        } catch (AccountPasswordAlreadyUsedException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            return "";
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
        return "home";
    }
}
