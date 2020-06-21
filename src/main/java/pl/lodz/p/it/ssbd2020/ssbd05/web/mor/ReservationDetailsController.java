package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ReservationDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Kontroler odpowiedzialny za wyświetlanie szczegółów wybranej rezerwacji.
 */
@Log
@Named
@ViewScoped
public class ReservationDetailsController implements Serializable {

    @Inject
    private ReservationDetailsEndpointLocal reservationDetailsEndpointLocal;

    @Getter
    private ReservationDTO reservationDTO;

    @Getter
    private ResourceBundles resourceBundles;

    @Getter
    @Setter
    private String extraServices;

    @Inject
    private CancelReservationController cancelReservationController;

    @Inject
    private ChangeReservationStatusController changeReservationStatusController;

    @Getter
    @Setter
    private boolean editable;

    /**
     * Metoda wykonywana przy otwarciu strony ze szczegółami rezerwacji i wczytująca dane wybranej rezerwacji
     */
    @PostConstruct
    public void init(){
        try {

            resourceBundles = new ResourceBundles();
            this.reservationDTO = reservationDetailsEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            editable=true;
            if(reservationDTO.getStatusName().equalsIgnoreCase(ReservationStatuses.cancelled.toString())
                    || reservationDTO.getStatusName().equalsIgnoreCase(ReservationStatuses.finished.toString())
                    || reservationDTO.getStatusName().equalsIgnoreCase(ReservationStatuses.paid.toString())) {
                editable = false;
            }
            cancelReservationController.setReservationDTO(this.reservationDTO);
            changeReservationStatusController.setReservationDTO(this.reservationDTO);
            String[] eventTypes = reservationDTO.getExtraServiceCollection().toArray(new String[0]);
            StringBuilder result = new StringBuilder();
            extraServices = "";
            for(int i=0; i< reservationDTO.getExtraServiceCollection().size(); i++){
                if (i != reservationDTO.getExtraServiceCollection().size() - 1) {
                    result.append(eventTypes[i]).append(", ");
                } else {
                    result.append(eventTypes[i]);
                }
            }
            extraServices = result.toString();
            if(!extraServices.isEmpty())
            extraServices = extraServices.replace("null", "");
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }

    /**
     * Metoda przenosząca użytkownika na stronę z listą wszystkich rezerwacji
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goBack(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedReservationNumber");
        return "back";
    }

    /**
     * Metoda anulująca rezerwację (o statusie "złożona") i odświeżająca stronę po tym.
     */
    public void cancelReservation() {
        cancelReservationController.cancelReservation();
        refresh();
    }

    /**
     * Metoda zmianiająca status rezerwacji i odświeżająca stronę po tym.
     */
    public void changeReservationStatus() {
        changeReservationStatusController.changeStatus();
        refresh();
    }

    /**
     * Metoda sprawdzająca czy status wybranej rezerwacji umożliwia jej anulowanie.
     *
     * @return boolean wartość logiczna informująca o tym czy rezerwację można anulować
     */
    public boolean isCancelable() {
        if(reservationDTO.getStatusName().equalsIgnoreCase(ReservationStatuses.cancelled.name()) ||
            reservationDTO.getStatusName().equalsIgnoreCase(ReservationStatuses.finished.name()))
            return false;
        else
            return true;
    }
    /**
     * Metoda sprawdzająca czy status wybranej rezerwacji to "zakończona"
     *
     * @return boolean wartość logiczna informująca o tym czy status rezerwacji to "zakończona"
     */
    public boolean isFinished() {
        return reservationDTO.getStatusName().equalsIgnoreCase(ReservationStatuses.finished.name());
    }

    /**
     * Metoda odpowiadająca za odświeżenie strony oraz pobranie obiektu ReservationDTO. Ustawia stan obiektów
     * ReservationDTO dla cancelReservationController oraz changeReservationStatus
     */
    private void refresh() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
            this.reservationDTO = reservationDetailsEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            cancelReservationController.setReservationDTO(this.reservationDTO);
            changeReservationStatusController.setReservationDTO(this.reservationDTO);
        } catch (AppBaseException | IOException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }


    /**
     * Metoda odpowiedzialna za przekierowanie użytkownika na stronę edycji rezerwacji
     *
     * @return ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goToEditPage(){
        return "edit";
    }

}
