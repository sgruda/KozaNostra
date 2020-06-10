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

    @PostConstruct
    public void init(){
        try {
            resourceBundles = new ResourceBundles();
            this.reservationDTO = reservationDetailsEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            cancelReservationController.setReservationDTO(this.reservationDTO);
            changeReservationStatusController.setReservationDTO(this.reservationDTO);
            for(String extraService: reservationDTO.getExtraServiceCollection()){
                extraServices += extraService;
                extraServices += " ";
            }
            extraServices = extraServices.replace("null", "");
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }
    public String goBack(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedReservationNumber");
        return "back";
    }
    public void cancelReservation() {
        cancelReservationController.cancelReservation();
        refresh();
    }
    public void changeReservationStatus() {
        log.severe("WTF ReservationDetailsController " + LocalDateTime.now());
        changeReservationStatusController.changeStatus();
        refresh();
    }
    public boolean isSubmitted() {
        return reservationDTO.getStatusName().equalsIgnoreCase(ReservationStatuses.submitted.toString());
    }

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

}
