package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import java.util.List;

public interface ListReviewsEndpointLocal {
    List<ReviewDTO> getAllReviews() throws AppBaseException;
}
