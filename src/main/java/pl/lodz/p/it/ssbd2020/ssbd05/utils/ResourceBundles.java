package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.PropertiesLoadingException;
import pl.lodz.p.it.ssbd2020.ssbd05.web.auth.RegistrationController;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class ResourceBundles {
    public static String getTranslatedText(String key) {
        return ResourceBundle.getBundle("i18n.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(key);
    }

    public static void emitErrorMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key),getTranslatedText(key));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public static void emitErrorMessageWithDetails(final String id, final String titleKey, final String detailsKey) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(titleKey), getTranslatedText(detailsKey));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public static void emitErrorMessageWithFlash(final String id, final String key) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key),getTranslatedText(key));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public static void emitDetailedErrorWithFlash(final String id, final String key, final String data) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key), data);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public static void emitMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key),getTranslatedText(key));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public static void emitMessageWithFlash(final String id, final String key) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key),getTranslatedText(key));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public static void emitDetailedMessageWithFlash(final String id, final String key, final String data) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key), data);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public static Properties loadProperties(String propertiesKey) throws AppBaseException {
        InputStream inputStream = ResourceBundles.class.getClassLoader().getResourceAsStream(propertiesKey);
        Properties properties = new Properties();
        try {
            if(inputStream != null)
                properties.load(inputStream);
        } catch (IOException e) {
                throw new PropertiesLoadingException();
        }
        return properties;
    }
}