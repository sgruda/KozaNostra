package pl.lodz.p.it.ssbd2020.ssbd05.dto.mos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa DTO reprezentująca adresy, do których przypisane są sale w systemie.
 * Jej instancje są wykorzystywane w warstwie prezentacji.
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class AddressDTO {

    private String street;
    private int streetNo;
    private String city;

    /**
     * Metoda odpowiedzialna za wyświetlenie informacji o danym adresie w formie przyjaznej dla użytkownika.
     *
     * @return Ciąg znaków zawierający nazwę ulicy, numer ulicy oraz miasto.
     */
    public String show() {
        return street + " " + streetNo + ", " + city;
    }
}
