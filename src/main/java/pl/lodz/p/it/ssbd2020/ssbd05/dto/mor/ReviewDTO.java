package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class ReviewDTO {

    private String content;
    private String date;
    private String clientLogin;
    private String reviewNumber;
}
