package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.Data;

@Data
public class AverageGuestNumberDTO {
    private Long guestSum;
    private Long eventSum;
    private Long average;
}
