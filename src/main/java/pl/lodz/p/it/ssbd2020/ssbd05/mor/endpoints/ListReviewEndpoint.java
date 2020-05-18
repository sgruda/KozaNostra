package pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListReviewEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.managers.ReviewManager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ListReviewEndpoint  implements Serializable, ListReviewEndpointLocal {

    @Inject
    private ReviewManager reviewManager;

    @Override
    @RolesAllowed("getReviewByReviewNumber")
    public ReviewDTO getReviewByReviewNumber(String reviewNumber) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed("removeReview")
    public void removeReview(ReviewDTO reviewDTO) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}
