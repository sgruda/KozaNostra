package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

@Named
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
public class RegisterAccountEndpoint implements Serializable {


    @Inject
    private AccountManager accountManager;

    @Getter
    @Setter
    private Account account;

    @Getter
    @Setter
    private Collection<AccessLevel> accessLevels;

    @PermitAll
    public void addNewAccount(AccountDTO accountDTO) throws AppBaseException {
        accessLevels = new ArrayList<>();
        account = new Account();
        generateAccessLevels(accountDTO);
        account.setAccessLevelCollection(accessLevels);
        account.setActive(accountDTO.isActive());
        account.setConfirmed(accountDTO.isConfirmed());
        account.setEmail(accountDTO.getEmail());
        account.setFirstname(accountDTO.getFirstname());
        account.setLastname(accountDTO.getLastname());
        account.setLogin(accountDTO.getLogin());
        account.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
        PreviousPassword previousPassword = new PreviousPassword();
        previousPassword.setPassword(account.getPassword());
        previousPassword.setAccount(account);

        int callCounter = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("numberOfTransactionRepeat"));
        do {
            accountManager.createAccount(account);
            callCounter--;
        } while (accountManager.isLastTransactionRollback() && callCounter > 0);
        if (callCounter == 0) {
            throw new ExceededTransactionRetriesException();
        }

    }

    public void generateAccessLevels(AccountDTO accountDTO) {
        for (String accessLevel : accountDTO.getAccessLevelCollection()) {
            if (accessLevel.equals("CLIENT")) {
                Client client = new Client();
                client.setAccount(account);
                client.setAccessLevel("CLIENT");
                client.setActive(true);
                accessLevels.add(client);
            }
        }
        Manager manager = new Manager();
        manager.setAccount(account);
        manager.setAccessLevel("MANAGER");
        manager.setActive(false);
        accessLevels.add(manager);

        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setAccessLevel("ADMIN");
        admin.setActive(false);
        accessLevels.add(admin);
    }



}
