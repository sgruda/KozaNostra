package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ReviewNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.WITH_SECONDS;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ReviewMapper {

    public static ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "date", dateFormat = WITH_SECONDS)
    @Mapping(source = "client", target = "clientLogin")
    @Mapping(source = "reservation", target = "reservationNumber")
    public abstract ReviewDTO toReviewDTO(Review review);

    @Mapping(target = "date", dateFormat = WITH_SECONDS)
    public abstract void updateReservationFromDTO(ReviewDTO reviewDTO, @MappingTarget Review review);

    public void updateAndCheckReservationFromDTO(ReviewDTO reviewDTO, Review review) throws AppBaseException {
        if(review == null) {
            throw new ReviewNotFoundException();
        }
        this.updateReservationFromDTO(reviewDTO, review);
    }

    public abstract ArrayList<ReviewDTO> toReviewDTOArrayList(Collection<Review> reviewCollection);

    public Review createNewReview(ReviewDTO reviewDTO){
        if ( reviewDTO == null ) {
            return null;
        }
        Review review = new Review();
        review.setContent( reviewDTO.getContent() );
        return review;
    }

    public String map(Client value){ return value.getAccount().getLogin(); }
    public String map(Reservation value){ return value.getReservationNumber();}
}
