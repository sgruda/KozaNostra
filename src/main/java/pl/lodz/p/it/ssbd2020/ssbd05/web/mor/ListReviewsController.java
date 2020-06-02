package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListReviewsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Log
@Named
@ViewScoped
public class ListReviewsController implements Serializable {
    @Inject
    private ListReviewsEndpointLocal listReviewsEndpoint;
    @Getter
    @Setter
    private List<ReviewDTO> reviews;
    @Getter
    @Setter
    private int pages;
    @Getter
    @Setter
    private int currentPage = 1;
    private final int reviewsPerPage = 2;
    @Getter
    @Setter
    private List<ReviewDTO> pageReviews;

    @PostConstruct
    public void init(){
        try {
            reviews = listReviewsEndpoint.getAllReviews();
            pages = reviews.size() / reviewsPerPage;
            if(reviews.size() % reviewsPerPage > 0){
                pages++;
            }
            updatePageReviews();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public void updatePageReviews(){
        final int firstReview = (currentPage - 1) * reviewsPerPage;
        final int lastReview = Math.min(((currentPage - 1) * reviewsPerPage) + reviewsPerPage, reviews.size());

        this.pageReviews = reviews.subList(firstReview,lastReview);
    }

    public void nextPage(){
        if(this.currentPage < pages){
            currentPage++;
        }
        updatePageReviews();
    }

    public boolean displayNextPage(){
        if(currentPage == pages){
            return false;
        }
        return true;
    }

    public void previousPage(){
        if(this.currentPage > 1){
            currentPage--;
        }
        updatePageReviews();
    }

    public boolean displayPreviousPage(){
        if(currentPage == 1){
            return false;
        }
        return true;
    }
}
