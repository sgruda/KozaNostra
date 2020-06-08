package pl.lodz.p.it.ssbd2020.ssbd05.mor;

import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.ReviewMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Review;

import java.time.LocalDateTime;

public class ReviewMapperTest {
    @Test
    public void mapReviewToDTO(){
        Review review = new Review();
        Client client = new Client();
        Account account = new Account();
        Reservation reservation = new Reservation();
        account.setLogin("Login");
        client.setAccount(account);
        review.setClient(client);
        review.setContent("Super Sprawa");
        review.setReservation(reservation);
        LocalDateTime date = LocalDateTime.of(2020,6,1,20,20);
        review.setDate(date);
        ReviewDTO dto = ReviewMapper.INSTANCE.toReviewDTO(review);
        Assert.assertEquals("Super Sprawa",dto.getContent());
        Assert.assertEquals("2020-06-01 20:20:00",dto.getDate());
        Assert.assertEquals("Login",dto.getClientLogin());
    }

}
