package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.AddReviewEndpointLocal;
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
import java.util.stream.Collectors;

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
    private List<String> reservationNumbers;

    @Setter
    @Getter
    private String reservationNumber;

    @Getter
    @Setter
    private String reviewText;

    private String login;




    @PostConstruct
    public void init(){
        login = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        try {
           reservations = userReservationsEndpoint.getUserReviewableReservations(login);
           reservationNumbers = reservations
                   .stream()
                   .map(ReservationDTO::getReservationNumber)
                   .collect(Collectors.toList());
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public void addReview(){
        ReviewDTO review = new ReviewDTO();
        review.setClientLogin(login);
        review.setContent(reviewText);
        review.setReservationNumber(reservationNumber);
        try {
            addReviewEndpoint.addReview(review);
        } catch (ValidationException e) {
            ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }

    public String addReviewLabel(String reservationNumber){
        ReservationDTO reservationDTO = null;
        for (ReservationDTO reservation: reservations
             ) {
            if(reservation.getReservationNumber().equals(reservationNumber)){
                reservationDTO = reservation;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(reservationDTO.getHallName());
        sb.append(" ");
        sb.append(reservationDTO.getStartDate());
        sb.append("-");
        sb.append(reservationDTO.getEndDate());
        return sb.toString();
    }

    public boolean isAvailableReservations(){
        return !reservations.isEmpty();
    }
}
