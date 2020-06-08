package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ChangeReservationStatusEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ReservationDetailsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Log
@Named
@ViewScoped
public class CancelReservationController implements Serializable {

    @Inject
    private ChangeReservationStatusEndpointLocal changeReservationStatusEndpointLocal;
    @Getter
    private ReservationDTO reservationDTO;
    @Getter
    private ResourceBundles resourceBundles;

    public void init(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
        try {
            resourceBundles = new ResourceBundles();
            changeReservationStatusEndpointLocal.getReservationByNumber(reservationDTO.getReservationNumber());
        } catch(AppOptimisticLockException e ) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null,"error.cancelreservation.optimisticlock");
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }

    public void cancelReservation() {
        reservationDTO.setStatusName(ReservationStatuses.cancelled.toString());
        try {
            changeReservationStatusEndpointLocal.cancelReservation(reservationDTO);
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

}
