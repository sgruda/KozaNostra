package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

public class ResourceBundles {
    public static String getTranslatedText(String key) {
        return ResourceBundle.getBundle("i18n.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(key);
    }

    public static void emitErrorMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,getTranslatedText(key),getTranslatedText(key));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void emitMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,getTranslatedText(key),getTranslatedText(key));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}