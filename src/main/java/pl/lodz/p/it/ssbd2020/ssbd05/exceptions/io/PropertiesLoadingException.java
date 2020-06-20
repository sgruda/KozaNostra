package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny rzucany w przypadku nieudanego wczytywania danych z pliku konfiguracyjnego.
 */
public class PropertiesLoadingException extends AppBaseException {

    /**
     * Domyślny klucz błędu.
     */
    static final public String KEY_PROPERTIES_LOADING = "error.io.properties.loading";

    /**
     * Konstruktor bezparametrowy.
     */
    public PropertiesLoadingException() {
        super(KEY_PROPERTIES_LOADING);
    }
}
