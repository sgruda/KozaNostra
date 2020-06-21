package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Klasa DTO zawierająca informacje o rezerwacji złożonej na salę przez klienta.
 * Jej instancje są wykorzystywane w warstwie prezentacji.
 */
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
    private ClientDTO clientDTO;
    private String reservationNumber;
    private String eventTypeName;
    private Long guestsNumber;

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO[reservation number= " + reservationNumber + "]";
    }
}
