package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;

import java.util.ArrayList;
import java.util.Collection;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.WITH_SECONDS;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ReviewMapper {

    public static ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "date", dateFormat = WITH_SECONDS)
    @Mapping(source = "client", target = "clientLogin")
    public abstract ReviewDTO toReviewDTO(Review review);

    public abstract ArrayList<ReviewDTO> toReviewDTOArrayList(Collection<Review> reviewCollection);

    public String map(Client value){
     return value.getAccount().getLogin();
    }
}
