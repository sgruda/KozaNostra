package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter.WITH_SECONDS;

/**
 * Interfejs konwertujący pomiędzy obiektami klas Account i AccountDTO,
 * którego implementacja jest generowana poprzez bibliotekę MapStruct w trakcie kompilacji.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    /**
     * Instancja implementacji interfejsu pobierana poprzez klasę narzędziową Mappers.
     */
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy Account na AccountDTO.
     *
     * @param Account Obiekt klasy Account.
     * @return Obiekt klasy AccountDTO.
     */
    @Mapping(target = "lastSuccessfulAuth", dateFormat = WITH_SECONDS)
    @Mapping(target = "lastFailedAuth", dateFormat = WITH_SECONDS)
    AccountDTO toAccountDTO(Account Account);

    /**
     * Metoda odpowiedzialna za konwersję z obiektu klasy AccountDTO na Account.
     *
     * @param accountDTO Obiekt klasy AccountDTO.
     * @return Obiekt klasy Account.
     */
    @Mapping(target = "lastSuccessfulAuth", dateFormat = WITH_SECONDS)
    @Mapping(target = "lastFailedAuth", dateFormat = WITH_SECONDS)
    Account createNewAccount(AccountDTO accountDTO);

    /**
     * Metoda odpowiedzialna za edycję obiektu klasy Account na podstawie zmian w obiekcie AccountDTO.
     *
     * @param accountDTO Obiekt klasy AccountDTO.
     * @param account    Obiekt klasy Account.
     */
    @Mapping(target = "lastSuccessfulAuth", dateFormat = WITH_SECONDS)
    @Mapping(target = "lastFailedAuth", dateFormat = WITH_SECONDS)
    void updateAccountFromDTO(AccountDTO accountDTO, @MappingTarget Account account);

    /**
     * Metoda odpowiedzialna za konwersję kolekcji obiektów klasy Account na kolekcję obiektów AccountDTO.
     *
     * @param accountCollection Kolekcja obiektów klasy Account.
     * @return Kolekcja obiektów klasy AccountDTO.
     */
    Collection<AccountDTO> toAccountDTOCollection(Collection<Account> accountCollection);

    /**
     * Metoda odpowiedzialna za konwersję kolekcji obiektów klasy AccessLevel na kolekcję ich nazw.
     * Zdefiniowanie domyślnej implementacji jest konieczne w celu wykorzystania jej w metodzie toAccountDTO.
     *
     * @param accessLevelCollection Kolekcja obiektów klasy AccessLevel.
     * @return Kolekcja nazw poziomów dostępu.
     */
    default Collection<String> toAccessLevelStringCollection(Collection<AccessLevel> accessLevelCollection) {
        return accessLevelCollection.stream()
                .filter(AccessLevel::getActive)
                .map(AccessLevel::getAccessLevel)
                .collect(Collectors.toList());
    }

    /**
     * Metoda odpowiedzialna za konwersję kolekcji nazw poziomów dostępu na kolekcję obiektów klasy AccessLevel.
     * Zdefiniowanie domyślnej implementacji jest konieczne w celu wykorzystania jej w metodach createNewAccount
     * i updateAccountFromDTO, jednak po ich wywołaniu należy ręcznie ustawić kolekcję AccessLevel.
     *
     * @param accessLevelStringCollection Kolekcja nazw poziomów dostępu.
     * @return Pusta kolekcja obiektów klasy AccessLevel.
     */
    default Collection<AccessLevel> toAccessLevelCollection(Collection<String> accessLevelStringCollection) {
        return new ArrayList<>();
    }
}
