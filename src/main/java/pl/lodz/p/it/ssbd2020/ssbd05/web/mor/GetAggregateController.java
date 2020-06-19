package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.AverageGuestNumberDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.GetAggregateEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;

/**
 * Kontroler odpowiedzialny za pobieranie danych o agregacie
 */
@Log
@Named
@RequestScoped
public class GetAggregateController {

    @Inject
    GetAggregateEndpointLocal aggregateEndpoint;

    private AverageGuestNumberDTO dto;

    /**
     * Metoda wykonywana po stworzeniu instancji klasy GetAggregateController. Pobiera obiekt agregatu z bazy
     */
    @PostConstruct
    public void init(){
        try {
           dto = aggregateEndpoint.getAggregate();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    /**
     * Metoda odpowiedzialna za zwrócenie średniej liczby gości w systemie
     *
     * @return Ciąg znaków reprezentujący średnią liczbę gości
     */
    public String getAverageGuestNumber(){
        if(dto != null){
            String s = ResourceBundles.getTranslatedText("guest.number.average");
            String result = MessageFormat.format(s,dto.getAverage());
            return result;
        }
        else return "";
    }

    /**
     * Metoda odpowiedzialna za zwrócenie ogólnej liczby gości w systemie
     *
     * @return Ciąg znaków reprezentujący liczbę gości
     */
    public String getTotalGuestNumber(){
        if(dto != null) {
            String s = ResourceBundles.getTranslatedText("guest.number.total");
            String result = MessageFormat.format(s, dto.getGuestSum());
            return result;
        }
        else return "";
    }
}
