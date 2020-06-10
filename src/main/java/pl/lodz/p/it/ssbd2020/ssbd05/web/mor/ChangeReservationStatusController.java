package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.NoncancelableReservationException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ChangeReservationStatusEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log
@Named
@ViewScoped
public class ChangeReservationStatusController implements Serializable {
    @Inject
    private ChangeReservationStatusEndpointLocal changeReservationStatusEndpointLocal;
    @Getter
    private ReservationDTO reservationDTO;
    @Getter
    private ResourceBundles resourceBundles;
    @Getter
    private List<String> statuses;
    @Getter @Setter
    private String newStatus;

    public void setReservationDTO(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
        this.newStatus = reservationDTO.getStatusName();
        statuses = convertReservationStatusesToString();
        try {
            resourceBundles = new ResourceBundles();
            changeReservationStatusEndpointLocal.getReservationByNumber(reservationDTO.getReservationNumber());
        } catch(AppOptimisticLockException e ) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null,"error.reservation.optimisticlock");
        } catch (AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }

    public void changeStatus() {
        reservationDTO.setStatusName(newStatus);
        try {
            changeReservationStatusEndpointLocal.changeReservationStatus(reservationDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.listreservations.reservation.details.change.status");
        } catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppOptimisticLockException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.reservation.optimisticlock");
        } catch(AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }
    private List<String> convertReservationStatusesToString() {
        List<String> ret = new ArrayList<>();
        for(ReservationStatuses status : ReservationStatuses.values())
            ret.add(status.name());
        return ret;
    }
    public String goBack() {
        return "reservationStatusHasBeenChanged";
    }
}
