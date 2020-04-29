package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

public class ResourceBundles {
    public static String getTranslatedText(String key) {
        return ResourceBundle.getBundle("i18n.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(key);
    }
}