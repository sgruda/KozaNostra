package pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io;

import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

public class PropertiesLoadingException extends AppBaseException {
    static final public String KEY_PROPERTIES_LOADING = "error.io.properties.loading";

    public PropertiesLoadingException(){ super(KEY_PROPERTIES_LOADING); }

    public PropertiesLoadingException(String msg){
        super(msg);
    }

    public PropertiesLoadingException(Throwable ex){
        super(KEY_PROPERTIES_LOADING, ex);
    }
}