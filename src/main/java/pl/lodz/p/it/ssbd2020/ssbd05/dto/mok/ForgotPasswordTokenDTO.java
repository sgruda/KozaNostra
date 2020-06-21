package pl.lodz.p.it.ssbd2020.ssbd05.dto.mok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Klasa DTO zawierająca informacje o jednorazowym kodzie wysyłanym na adres email użytkownika w przypadku,
 * gdy zażąda on zresetowania hasła. Jej instancje są wykorzystywane w warstwie prezentacji.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForgotPasswordTokenDTO {

    private LocalDateTime expireDate;
    private String hash;
    private String account;

    @Override
    public String toString(){
        return "pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.ForgotPasswordTokenDTO";
    }
}
