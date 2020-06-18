package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego AddReviewEndpoint, pośredniczącemu
 * przy dodawaniu opinii.
 */
@Local
public interface AddReviewEndpointLocal {
    /**
     * Metoda odpowiedzialna za dodawanie nowej opinii
     *
     * @param reviewDTO opinia
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void addReview(ReviewDTO reviewDTO) throws AppBaseException;
}
