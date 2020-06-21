package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListReviewsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Kontroler odpowiedzialny za wyświetlanie i stronicowanie listy opinii.
 */
@Log
@Named
@ViewScoped
public class ListReviewsController implements Serializable {
    @Inject
    private ListReviewsEndpointLocal listReviewsEndpoint;
    @Getter
    private List<ReviewDTO> reviews;
    @Getter
    private int pages;
    @Getter
    private int currentPage = 1;
    @Getter
    private int reviewsPerPage = 2;
    @Getter
    private List<ReviewDTO> pageReviews;

    /**
     *  Metoda odpowiedzialna za wczytanie wszystkich opinii.
     *  Wykonywana po stworzeniu obiektu klasy ListReviewsController.
     */
    @PostConstruct
    public void init(){
        try {
            reviews = listReviewsEndpoint.getAllReviews();
            updatePageReviews();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    /**
     * Metoda odpowiedzialna za zaktualizowanie listy rezerwacji do wyświetlenie na aktualnie wybranej stronie.
     */
    public void updatePageReviews(){
        pages = reviews.size() / reviewsPerPage;
        if(reviews.size() % reviewsPerPage > 0){
            pages++;
        }
        final int firstReview = (currentPage - 1) * reviewsPerPage;
        final int lastReview = Math.min(((currentPage - 1) * reviewsPerPage) + reviewsPerPage, reviews.size());

        this.pageReviews = reviews.subList(firstReview,lastReview);
    }

    /**
     * Metoda odpowiedzialna za przejście do następnej strony opinii.
     */
    public void nextPage(){
        if(this.currentPage < pages){
            currentPage++;
        }
        updatePageReviews();

    }

    /**
     * Metoda sprawdzająca, czy powinien zostać wyświetlony przycisk przekierowujący do kolejnej strony listy.
     *
     * @return boolean
     */
    public boolean displayNextPage(){
        if(currentPage == pages || pages == 0){
            return false;
        }
        return true;
    }

    /**
     * Metoda odpowiedzialna za przejście do poprzedniej strony opinii.
     */
    public void previousPage(){
        if(this.currentPage > 1){
            currentPage--;
        }
        updatePageReviews();
    }

    /**
     * Metoda sprawdzaająca, czy powinien zostać wyświetlony przycisk przekierowujący do poprzedniej strony listy.
     *
     * @return the boolean
     */
    public boolean displayPreviousPage(){
        if(currentPage <= 1){
            return false;
        }
        return true;
    }

    /**
     * Metoda odpowiedzialna za zmianę liczby opinii na stronę.
     *
     * @param reviewsPerPage liczba opinii na stronę
     */
    public void setReviewsPerPage(int reviewsPerPage){
        this.reviewsPerPage = reviewsPerPage;
        currentPage = 1;
        updatePageReviews();
    }

    /**
     * Metoda zmieniająca numer aktualnie wybranej strony
     *
     * @param currentPage numer strony
     */
    public void setCurrentPage(int currentPage){
        if(currentPage > pages){
            currentPage = pages;
        }
        else if(currentPage < 1){
            currentPage = 1;
        }
        this.currentPage = currentPage;
        updatePageReviews();
    }

    /**
     * Metoda zwracająca liczbę znaków potrzebnych do zapisania liczby stron
     *
     * @return liczba znaków potrzebnych do zapisania liczby stron
     */
    public int getPagesDigits(){
        return String.valueOf(pages).length();
    }

    /**
     * Metoda przekierowująca do edycji opinii.
     *
     * @param reviewNumber numer opinii
     * @return Ciąg znaków, dla którego została zdefiniowana zasada nawigacji w deskryptorze faces-config.xml
     */
    public String selectReview(String reviewNumber) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedReview", reviewNumber);
        return "editReview";
    }

    /**
     * Metoda weryfikująca, czy uwierzytelniony użytkownik jest autorem opinii.
     *
     * @param reviewOwnerLogin login autora opinii
     * @return boolean
     */
    public boolean isOwnerOfOpinion(String reviewOwnerLogin) {
        String currentUser;
        currentUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        if(null == currentUser)
            currentUser = "";
        if(reviewOwnerLogin.contains(currentUser) && currentUser.contains(reviewOwnerLogin))
            return true;
        else
            return false;
    }
}
