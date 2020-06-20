package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.ForgotPasswordTokenDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.ForgotPasswordToken;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.WITH_SECONDS;

/**
 * Interfejs konwertujący pomiędzy obiektami klas ForgotPasswordToken i ForgotPasswordTokenDTO,
 * którego implementacja jest generowana poprzez bibliotekę MapStruct w trakcie kompilacji.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ForgotPasswordTokenMapper {

    /**
     * Instancja implementacji interfejsu pobierana poprzez klasę narzędziową Mappers.
     */
    ForgotPasswordTokenMapper INSTANCE = Mappers.getMapper(ForgotPasswordTokenMapper.class);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy ForgotPasswordToken na ForgotPasswordTokenDTO.
     *
     * @param forgotPasswordToken Obiekt klasy ForgotPasswordToken.
     * @return Obiekt klasy ForgotPasswordTokenDTO.
     */
    @Mapping(target = "expireDate", dateFormat = WITH_SECONDS)
    ForgotPasswordTokenDTO toTokenDTO(ForgotPasswordToken forgotPasswordToken);

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy Account na ciąg znaków będący jego nazwą użytkownika.
     * Zdefiniowanie domyślnej implementacji jest konieczne w celu wykorzystania jej w metodzie toTokenDTO.
     *
     * @param account Obiekt klasy Account.
     * @return Nazwa użytkownika.
     */
    default String toAccountString(Account account) {
        return account.getLogin();
    }
}
