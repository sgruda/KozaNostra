package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public @Data class ReservationDTO {

    private Long id;
    private Date startDate;
    private Date endDate;
    private double totalPrice;
    private String statusName;
    private Collection<String> extraServiceCollection = new ArrayList<>();
    private String hallName;
    private String clientLogin;
}
