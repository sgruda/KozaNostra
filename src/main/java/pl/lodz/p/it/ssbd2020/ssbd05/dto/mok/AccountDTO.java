package pl.lodz.p.it.ssbd2020.ssbd05.dto.mok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public @Data class AccountDTO {

    private Long id;
    private String login;
    private String password;
    private boolean active;
    private boolean confirmed;
    private Collection<String> accessLevelCollection = new ArrayList<>();
    private String firstname;
    private String lastname;
    private String email;
    private Date lastSuccessfulAuth;
    private Date lastFailedAuth;
    private String lastAuthIp;
    private int failedAuthCounter;
    private boolean forcePasswordChange = true;

    public AccountDTO(Long id, String login, String password, boolean active, boolean confirmed, String firstname, String lastname, String email, int failedAuthCounter, boolean forcePasswordChange) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.active = active;
        this.confirmed = confirmed;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.failedAuthCounter = failedAuthCounter;
        this.forcePasswordChange = forcePasswordChange;
    }
}
