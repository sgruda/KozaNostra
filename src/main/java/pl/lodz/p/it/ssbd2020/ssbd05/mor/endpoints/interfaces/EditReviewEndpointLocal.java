package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;

/**
 * Interfejs dla punktu dostępowego EditReviewEndpoint, pośredniczącemu
 * przy edycji opinii.
 */
@Local
public interface EditReviewEndpointLocal {
    /**
     * Metoda odpowiedzialna za pobieranie opinii na podstawie jej numeru
     *
     * @param reviewNumber Numer opinii
     * @return Obiekt transferowy reprezentujący opinię z podanym numerem
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    ReviewDTO getReviewByReviewNumber(String reviewNumber) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za edycję opinii
     *
     * @param reviewDTO  Obiekt transferowy typu ReviewDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void editReview(ReviewDTO reviewDTO) throws AppBaseException;

    /**
     * Metoda odpowiedzialna za usunięcie opinii
     *
     * @param reviewDTO Obiekt transferowy typu ReviewDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    void removeReview(ReviewDTO reviewDTO) throws AppBaseException;
}
