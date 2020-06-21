package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Data;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListUserReservationsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Kontroler odpowiedzialny za wyświetlanie listy rezerwacji zalogowanego klienta
 */
@Log
@Named
@Data
@ViewScoped
public class ListUsersReservationController implements Serializable {


    @Inject
    private ListUserReservationsEndpointLocal userReservationsEndpointLocal;

    private List<ReservationDTO> usersReservations;

    private ResourceBundles resourceBundles;
    /**
     * Metoda odpowiedzialna za wczytanie wszystkich rezerwacji zalogowanego użytkownika.
     * Wykonywana po stworzeniu instancji klasy ListUsersReservationController.
     */
    @PostConstruct
    private void init() {
        try {
            resourceBundles = new ResourceBundles();
            usersReservations = userReservationsEndpointLocal.getAllUsersReservations(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
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
