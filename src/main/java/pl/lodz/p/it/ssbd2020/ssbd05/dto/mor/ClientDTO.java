package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class ClientDTO {
    private String login;
    private String firstname;
    private String lastname;
    private String email;

    public String getPersonalDetails() {
        return firstname + " " + lastname;
    }
}
