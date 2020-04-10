package pl.lodz.p.it.ssbd2020.ssbd05.dto.mos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class AddressDTO {

    private Long id;
    private String street;
    private String postalCode;
    private int streetNo;
    private String city;
}
