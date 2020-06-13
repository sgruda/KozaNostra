package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
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
    private List<ReviewDTO> reviews;
    @Getter
    private int pages;
    @Getter
    private int currentPage = 1;
    @Getter
    private int reviewsPerPage = 2;
    @Getter
    private List<ReviewDTO> pageReviews;

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

    public void updatePageReviews(){
        pages = reviews.size() / reviewsPerPage;
        if(reviews.size() % reviewsPerPage > 0){
            pages++;
        }
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

    public void setReviewsPerPage(int reviewsPerPage){
        this.reviewsPerPage = reviewsPerPage;
        currentPage = 1;
        updatePageReviews();
    }

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

    public int getPagesDigits(){
        return String.valueOf(pages).length();
    }

}
