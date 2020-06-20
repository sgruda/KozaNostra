package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.Local;
import java.util.List;

/**
 * Interfejs dla punktu dostępowego ListReviewsEndpoint pośredniczącego
 * przy wyświetlaniu listy wszystkich opinii
 */
@Local
public interface ListReviewsEndpointLocal {
    /**
     * Metoda pobierająca listę wszystkich opinii.
     *
     * @return lista wszystkich opinii w postacji obiektów typu ReviewDTO
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    List<ReviewDTO> getAllReviews() throws AppBaseException;
}
