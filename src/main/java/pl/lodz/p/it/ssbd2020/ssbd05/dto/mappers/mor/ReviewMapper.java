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

/**
 * Klasa abstrakcyjna konwertująca pomiędzy obiektami klas Review i ReviewDTO.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ReviewMapper {

    /**
     * Instancja klasy pobierana poprzez klasę narzędziową Mappers.
     */
    public static ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy Review na ReviewDTO.
     *
     * @param review Obiekt klasy Review.
     * @return Obiekt klasy ReviewDTO.
     */
    @Mapping(target = "date", dateFormat = WITH_SECONDS)
    @Mapping(source = "client", target = "clientLogin")
    @Mapping(source = "reservation", target = "reservationNumber")
    public abstract ReviewDTO toReviewDTO(Review review);

    /**
     * Metoda odpowiedzialna za edycję obiektu klasy Review na podstawie zmian w obiekcie ReviewDTO.
     *
     * @param reviewDTO Obiekt klasy ReviewDTO.
     * @param review    Obiekt klasy Review.
     */
    @Mapping(target = "date", dateFormat = WITH_SECONDS)
    public abstract void updateReviewFromDTO(ReviewDTO reviewDTO, @MappingTarget Review review);

    /**
     * Metoda sprawdzająca, czy recenzja istnieje i edytująca ją na podstawie zmian w obiekcie ReviewDTO.
     *
     * @param reviewDTO Obiekt klasy ReviewDTO.
     * @param review    Obiekt klasy Review.
     * @throws AppBaseException Podstawowy wyjątek aplikacyjny.
     */
    public void updateAndCheckReviewFromDTO(ReviewDTO reviewDTO, Review review) throws AppBaseException {
        if(review == null) {
            throw new ReviewNotFoundException();
        }
        this.updateReviewFromDTO(reviewDTO, review);
    }

    /**
     * Metoda odpowiedzialna za konwersję listy obiektów klasy Review na listę obiektów ReviewDTO.
     *
     * @param reviewCollection Lista obiektów klasy Review.
     * @return Lista obiektów klasy ReviewDTO.
     */
    public abstract ArrayList<ReviewDTO> toReviewDTOArrayList(Collection<Review> reviewCollection);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy ReviewDTO na Review.
     *
     * @param reviewDTO Obiekt klasy ReviewDTO.
     * @return Obiekt klasy Review.
     */
    public Review createNewReview(ReviewDTO reviewDTO){
        if ( reviewDTO == null ) {
            return null;
        }
        Review review = new Review();
        review.setContent( reviewDTO.getContent() );
        return review;
    }

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy Client na ciąg znaków będący jego nazwą użytkownika.
     *
     * @param value Obiekt klasy Client.
     * @return Nazwa użytkownika.
     */
    public String map(Client value) {
        return value.getAccount().getLogin();
    }

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy Reservation na ciąg znaków będący jej numerem identyfikacyjnym.
     *
     * @param value Obiekt klasy Reservation.
     * @return Numer identyfikacyjny rezerwacji.
     */
    public String map(Reservation value) {
        return value.getReservationNumber();
    }
}
