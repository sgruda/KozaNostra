package pl.lodz.p.it.ssbd2020.ssbd05.web.utils;

import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

/**
 * Walidator sprawdzający, czy hasła się zgadzają
 */
@Named
@RequestScoped
public class PasswordValidator {

    /**
     * Validate password match.
     *
     * @param context   Kontekst warstwy prezentacji
     * @param component Komponent widoku
     * @param value     Wartość hasła
     */
    public void validatePasswordMatch(FacesContext context, UIComponent component,
                                      Object value) {
        String confirmPassword = (String) value;
        UIInput passwordInput = (UIInput) component.findComponent("password");
        String password = (String) passwordInput.getLocalValue();

        if (password == null || !password.equals(confirmPassword)) {
            String key = "page.registration.account.password.matchfail";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundles.getTranslatedText(key),ResourceBundles.getTranslatedText(key));
            throw new ValidatorException(msg);
        }
    }

}
