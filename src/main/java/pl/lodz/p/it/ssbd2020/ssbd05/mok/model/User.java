package pl.lodz.p.it.ssbd2020.ssbd05.mok.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class User {

    private String username;
    private String password;

}
