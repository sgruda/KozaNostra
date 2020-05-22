package pl.lodz.p.it.ssbd2020.ssbd05.web.mok;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ResendActivationEmailEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ResendActivationEmailController {

    @Inject
    private ResendActivationEmailEndpointLocal resendActivationEmailEndpoint;

    public void resendEmail(String login) {
        try {
            resendActivationEmailEndpoint.resendEmail(login);
            ResourceBundles.emitMessageWithFlash(null, "page.accountdetails.mail.sent");
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }
}
