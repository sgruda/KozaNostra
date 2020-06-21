package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.PropertiesLoadingException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Klasa narzędziowa do pobierania danych z plików konfiguracyjnych na podstawie
 * ich kluczy i wyświetlania komunikatów w warstwie prezentacji.
 */
@Log
public class ResourceBundles {

    /**
     * Metoda zwracająca wartość wpisu z deskryptora na podstawie jego klucza.
     *
     * @param key Ciąg znaków będący kluczem.
     * @return Zinternacjonalizowana wartość wpisu.
     */
    public static String getTranslatedText(String key) {
        return ResourceBundle.getBundle("i18n.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(key);
    }

    /**
     * Metoda wyświetlająca komunikat błędu w warstwie prezentacji.
     *
     * @param id      ID komunikatu.
     * @param message Treść komunikatu.
     */
    public static void emitErrorMessageByPlainText(final String id, final String message) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    /**
     * Metoda wyświetlająca komunikat błędu w warstwie prezentacji na podstawie klucza wpisu z deskryptora.
     *
     * @param id  ID komunikatu.
     * @param key Klucz wpisu z deskryptora.
     */
    public static void emitErrorMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key), " ");
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    /**
     * Metoda wyświetlająca szczegółowy komunikat błędu w warstwie prezentacji na podstawie klucza wpisu z deskryptora.
     *
     * @param id         ID komunikatu.
     * @param titleKey   Tytuł komunikatu.
     * @param detailsKey Klucz wpisu z deskryptora.
     */
    public static void emitErrorMessageWithDetails(final String id, final String titleKey, final String detailsKey) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(titleKey), getTranslatedText(detailsKey));
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    /**
     * Metoda wyświetlająca komunikat błędu, który jest zachowywany po przejściu na inną stronę
     * w warstwie prezentacji na podstawie klucza wpisu z deskryptora.
     *
     * @param id  ID komunikatu.
     * @param key Klucz wpisu z deskryptora.
     */
    public static void emitErrorMessageWithFlash(final String id, final String key) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key), " ");
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    /**
     * Metoda wyświetlająca szczegółowy komunikat błędu, który jest zachowywany po przejściu na inną stronę
     * w warstwie prezentacji na podstawie klucza wpisu z deskryptora.
     *
     * @param id   ID komunikatu.
     * @param key  Klucz wpisu z deskryptora.
     * @param data Treść komunikatu.
     */
    public static void emitDetailedErrorWithFlash(final String id, final String key, final String data) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getTranslatedText(key), data);
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    /**
     * Metoda wyświetlająca komunikat w warstwie prezentacji na podstawie klucza wpisu z deskryptora.
     *
     * @param id  ID komunikatu.
     * @param key Klucz wpisu z deskryptora.
     */
    public static void emitMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key), " ");
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    /**
     * Metoda wyświetlająca komunikat, który jest zachowywany po przejściu
     * na inną stronę w warstwie prezentacji na podstawie klucza wpisu z deskryptora.
     *
     * @param id  ID komunikatu.
     * @param key Klucz wpisu z deskryptora.
     */
    public static void emitMessageWithFlash(final String id, final String key) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key), " ");
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    /**
     * Metoda wyświetlająca szczegółowy komunikat, który jest zachowywany po przejściu
     * na inną stronę w warstwie prezentacji na podstawie klucza wpisu z deskryptora.
     *
     * @param id  ID komunikatu.
     * @param key Klucz wpisu z deskryptora.
     * @param data Treść komunikatu.
     */
    public static void emitDetailedMessageWithFlash(final String id, final String key, final String data) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getTranslatedText(key), data);
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    /**
     * Metoda wczytująca dane z pliku konfiguracyjnego.
     *
     * @param propertiesKey Nazwa pliku konfiguracyjnego.
     * @return Obiekt typu Properties zawierający klucze i wartości wpisów z danego pliku konfiguracyjnego.
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
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

    /**
     * Metoda pobierająca maksymalną ilość ponowień nieudanej transakcji z deskryptora.
     *
     * @return Maksymalna ilość ponowień transakcji.
     */
    public static int getTransactionRepeatLimit() {
        return Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("numberOfTransactionRepeat"));
    }
}