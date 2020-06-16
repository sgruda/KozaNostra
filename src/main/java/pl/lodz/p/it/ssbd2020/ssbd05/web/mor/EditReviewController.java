package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReviewNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReviewEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Kontroler odpowiedzialny za edycję opinii
 */
@Log
@Named
@ViewScoped
public class EditReviewController implements Serializable {
    @Inject
    private EditReviewEndpointLocal editReviewEndpointLocal;

    @Getter
    @Setter
    private ReviewDTO reviewDTO;

    /**
     * Metoda wykonywana przy przejściu na stronę z edycją opinii, wczytuje dane wybranej opinii
     *
     * @return Ciąg znaków, który po pomyślnym wczytaniu danych opinii powoduje pozostanie na stronie,
     * natomiast w przeciwnym wypadku powraca do strony z listą wszystkich opinii
     */
    public String onLoad(){
        String selectedReview = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReview");
        try {
            this.reviewDTO = editReviewEndpointLocal.getReviewByReviewNumber(selectedReview);
        } catch (ReviewNotFoundException ex){
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, ex.getMessage());
            return goBack();
        } catch (AppBaseException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
        return "";
    }

    /**
     * Metoda odpowiedzialna za usunięcie opinii.
     */
    public void removeReview() {
        try {
            editReviewEndpointLocal.removeReview(reviewDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.reviews.remove.success");
        } catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "page.reviews.edit.optimisticlock");
        } catch (ReviewNotFoundException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "page.reviews.remove.failed.notfound");
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }

    /**
     * Metoda przenosząca użytkownika na stronę z listą wszystkich opinii
     *
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String goBack() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedReview");
        return "listReviews";
    }

    /**
     * Metoda odpowiedzialna za edycję opinii
     */
    public void editReview(){
        try {
            reviewDTO.setDate(DateFormatter.formatDate(LocalDateTime.now()));
            editReviewEndpointLocal.editReview(reviewDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.review.edit.success");
        } catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "page.reviews.edit.optimisticlock");
        } catch (ReviewNotFoundException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "page.reviews.remove.failed.notfound");
        } catch (AppBaseException e) {
            ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
        }
    }

}
