package pl.lodz.p.it.ssbd2020.ssbd05.dto.mok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

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

    public AccountDTO(String login, String password, boolean active, boolean confirmed, String firstname, String lastname, String email, String veryficationToken, int failedAuthCounter) {
        this.login = login;
        this.password = password;
        this.active = active;
        this.confirmed = confirmed;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.veryficationToken = veryficationToken;
        this.failedAuthCounter = failedAuthCounter;
    }
}
