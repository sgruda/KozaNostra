package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa DTO reprezentująca poziom dostępu Klient.
 * Jej instancje są wykorzystywane w warstwie prezentacji.
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class ClientDTO {
    private String login;
    private String firstname;
    private String lastname;
    private String email;

    /**
     * Metoda odpowiedzialna za pobranie godności klienta.
     *
     * @return Ciąg znaków zawierający skonkatenowane imię i nazwisko klienta.
     */
    public String getPersonalDetails() {
        return firstname + " " + lastname;
    }
}
