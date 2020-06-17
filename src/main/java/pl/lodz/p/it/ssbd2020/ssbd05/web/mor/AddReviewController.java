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

/**
 * Kontroler odpowiedzialny za dodawanie nowej opinii
 */
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


    /**
     * Metoda wykonywana po stworzeniu obiektu klasy AddReviewController.
     * Pobiera listę rezerwacji, dla których możliwe jest wystawienie opinii.
     */
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

    /**
     * Metoda dodająca nową opinie.
     * Po dodaniu nowej opinii następuje przekierowanie na stronę z listą wszystkich opinii.
     *
     * @return string
     */
    public String addReview(){
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
        ResourceBundles.emitMessageWithFlash(null,"page.addReview.created");
        return "addReview";
    }

    /**
     * Metoda zwracająca identyfikator rezerwacji do wyświetlenia w menu rozwijanym.
     *
     * @param reservationNumber numer rezerwacji
     * @return string
     */
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

    /**
     * Metoda weryfikująca, czy dostępna jest przynajmniej jedna rezerwacja dla której możnaby wystawić opinię.
     *
     * @return boolean
     */
    public boolean isAvailableReservations(){
        return !reservations.isEmpty();
    }
}
