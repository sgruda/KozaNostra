package pl.lodz.p.it.ssbd2020.ssbd05.dto.mok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Klasa DTO zawierająca informacje o koncie użytkownika.
 * Jej instancje są wykorzystywane w warstwie prezentacji.
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class AccountDTO {

    private String login;
    private String password;
    private boolean active;
    private boolean confirmed;
    private Collection<String> accessLevelCollection = new ArrayList<>();
    private String firstname;
    private String lastname;
    private String email;
    private String lastSuccessfulAuth;
    private String lastFailedAuth;
    private String lastAuthIp;
    private String veryficationToken;
    private int failedAuthCounter;

    @Override
    public String toString(){
        return "pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO[login= " + login + "]";
    }
}
