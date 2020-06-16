package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
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

/**
 * Kontroler odpowiedzialny za anulowanie wybranej rezerwacji.
 */
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

    /**
     * Metoda ustawiająca w kontrolerze obiekt transferowy anulowanej rezerwacji
     *
     * @param reservationDTO obiekt typu ReservationDTO
     */
    public void setReservationDTO(ReservationDTO reservationDTO) {
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

    /**
     * Metoda odpowiedzialna za anulowanie wybranej rezerwacji.
     */
    public void cancelReservation() {
        reservationDTO.setStatusName(ReservationStatuses.cancelled.toString());
        try {
            changeReservationStatusEndpointLocal.cancelReservation(reservationDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.client.reservations.details.cancel.success");
        } catch (ExceededTransactionRetriesException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppOptimisticLockException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.cancelreservation.optimisticlock");
        } catch(NoncancelableReservationException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.reservation.noncancelable");
        } catch(AppBaseException appBaseException) {
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, appBaseException.getMessage());
        }
    }

}
