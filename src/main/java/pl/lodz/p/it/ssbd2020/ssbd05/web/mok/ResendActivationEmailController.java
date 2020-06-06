package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ResendActivationEmailEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;

/**
 * Kontroler odpowiedzialny za wysyłanie wiadomości z linkiem aktywacyjnym
 */
@Log
@Named
@RequestScoped
public class ResendActivationEmailController {

    @Inject
    private ResendActivationEmailEndpointLocal resendActivationEmailEndpoint;

    /**
     * Resend email.
     *
     * @param login nazwa użytkownika do którego ma zostać wysłany link aktywacyjny
     */
    public void resendEmail(String login) {
        try {
            resendActivationEmailEndpoint.resendEmail(login);
            ResourceBundles.emitMessageWithFlash(null, "page.accountdetails.mail.sent");
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }
}
