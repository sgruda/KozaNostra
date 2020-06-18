package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
/**
 * Kontroler odpowiedzialny za wyświetlanie listy rezerwacji wszystkich użytkowników
 */
@Log
@Named
@ViewScoped
public class ListReservationsController implements Serializable {
    @Inject
    private ListReservationEndpointLocal listReservationEndpointLocal;
    @Getter
    @Setter
    private List<ReservationDTO> reservations;
    @Getter
    private ResourceBundles resourceBundles;
    @Getter
    @Setter
    private String reservationFilter;

    /**
     * Metoda odpowiedzialna za wczytanie wszystkich rezerwacji.
     * Wykonywana po stworzeniu instancji klasy ListReservationsController.
     */
    @PostConstruct
    public void init() {
        try {
            reservations = listReservationEndpointLocal.getAllReservations();
            resourceBundles = new ResourceBundles();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    /**
     * Metoda odpowiedzialna za wczytanie przefiltrowanej listy rezerwacji.
     */
    public void filterReservations(){
        try {
            reservations = listReservationEndpointLocal.filterReservations(reservationFilter);
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }
    /**
     * Metoda przenosząca użytkownika na stronę ze szczegółami rezeracji
     *
     * @param reservationNumber numer wybranej rezerwacji
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goToDetails(String reservationNumber){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedReservationNumber", reservationNumber);
        return "reservationDetails";
    }
}
