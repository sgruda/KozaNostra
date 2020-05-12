package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
public @Data class ReservationDTO {

    private String reviewNumber;
    private String startDate;
    private String endDate;
    private double totalPrice;
    private String statusName;
    private Collection<String> extraServiceCollection = new ArrayList<>();
    private String hallName;
    private String clientLogin;
    private String reservationNumber;
    private String eventTypeName;
}
