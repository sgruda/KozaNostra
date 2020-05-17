package pl.lodz.p.it.ssbd2020.ssbd05.web;

import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.List;

@FacesValidator("atLeastOneCheckedValidator")
public class SelectManyCheckboxValidator implements Validator<List<String>> {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, List<String> o) throws ValidatorException {
        if (o.size() < 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundles.getTranslatedText("page.addaccount.noaccesslevels"), ResourceBundles.getTranslatedText("page.addaccount.noaccesslevels")));
        }
    }
}
