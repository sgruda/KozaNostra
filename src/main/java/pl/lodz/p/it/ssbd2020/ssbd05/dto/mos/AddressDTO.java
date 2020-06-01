package pl.lodz.p.it.ssbd2020.ssbd05.dto.mos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class AddressDTO {

    private String street;
    private int streetNo;
    private String city;

    public String show() {
        return street + " " + streetNo + ", " + city;
    }
}
