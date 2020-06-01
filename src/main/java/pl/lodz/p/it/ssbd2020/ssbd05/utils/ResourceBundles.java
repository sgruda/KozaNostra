package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.PropertiesLoadingException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

@Log
@Named
public class ResourceBundles {
    public static String getTranslatedText(String key) {
        return ResourceBundle.getBundle("i18n.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(key);
    }

    public static void emitErrorMessageByPlainText(final String id, final String message) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    public static void emitErrorMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key), " ");
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }
    public static void emitErrorMessageWithDetails(final String id, final String titleKey, final String detailsKey) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(titleKey), getTranslatedText(detailsKey));
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }
    public static void emitErrorMessageWithFlash(final String id, final String key) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key), " ");
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }
    public static void emitDetailedErrorWithFlash(final String id, final String key, final String data) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key), data);
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }
    public static void emitMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key), " ");
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }
    public static void emitMessageWithFlash(final String id, final String key) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key), " ");
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }
    public static void emitDetailedMessageWithFlash(final String id, final String key, final String data) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key), data);
        FacesContext.getCurrentInstance().addMessage(id, msg);
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

    public static int getTransactionRepeatLimit() {
        return Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("numberOfTransactionRepeat"));
    }
}