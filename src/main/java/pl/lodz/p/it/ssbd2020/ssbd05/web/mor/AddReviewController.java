package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.AddReviewEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListUserReservationsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Log
@ViewScoped
@Named
public class AddReviewController implements Serializable {
    @Inject
    ListUserReservationsEndpointLocal userReservationsEndpoint;

    @Inject
    AddReviewEndpointLocal addReviewEndpoint;

    @Getter
    @Setter
    private List<ReservationDTO> reservations;

    @Getter
    @Setter
    private ReservationDTO reservation;

    @Getter
    @Setter
    private String reviewText;




    @PostConstruct
    public void init(){
        String login = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        try {
           reservations = userReservationsEndpoint.getUserReviewableReservations(login);
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public String addReviewLabel(ReservationDTO reservationDTO){
        StringBuilder sb = new StringBuilder();
        sb.append(reservationDTO.getHallName());
        sb.append(" ");
        sb.append(reservationDTO.getStartDate());
        sb.append(":");
        sb.append(reservationDTO.getEndDate());
        return sb.toString();
    }

    public boolean isAvailableReservations(){
        return !reservations.isEmpty();
    }
}
