package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toAccountDTO(Account Account); //do wyswietlania
    Account createNewAccount(AccountDTO accountDTO); //do tworzenia nowych
    void updateAccountFromDTO(AccountDTO accountDTO, @MappingTarget Account account); //do update'u, nie ustawia accessLevelCollection
    Collection<AccountDTO> toAccountDTOCollection(Collection<Account> accountCollection); //do wyswietlania listy

    //metoda uzywana w toAccountDTO
    default Collection<String> toAccessLevelStringCollection(Collection<AccessLevel> accessLevelCollection) {
        return accessLevelCollection.stream()
                .filter(AccessLevel::getActive)
                .map(AccessLevel::getAccessLevel)
                .collect(Collectors.toList());
    }

    //metoda uzywana w createNewAccount i updateAccountFromDTO
    default Collection<AccessLevel> toAccessLevelCollection(Collection<String> accessLevelStringCollection) {
        return new ArrayList<>();
    }
}
