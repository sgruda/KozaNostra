package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa DTO zawierająca informacje o recenzji wystawionej przez klienta.
 * Jej instancje są wykorzystywane w warstwie prezentacji.
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class ReviewDTO {

    private String content;
    private String date;
    private String clientLogin;
    private String reviewNumber;
    private String reservationNumber;

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReviewDTO[review number= " + reviewNumber + "]";
    }
}
