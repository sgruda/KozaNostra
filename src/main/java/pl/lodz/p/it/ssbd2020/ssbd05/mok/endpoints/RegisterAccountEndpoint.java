package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.RegisterAccountEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

@Slf4j
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
public class RegisterAccountEndpoint implements Serializable, RegisterAccountEndpointLocal {

    @Inject
    private AccountManager accountManager;

    @Getter
    @Setter
    private Account account;

    @Getter
    @Setter
    private Collection<AccessLevel> accessLevels;

    @Override
    @PermitAll
    public void addNewAccount(AccountDTO accountDTO) throws AppBaseException {
        account = AccountMapper.INSTANCE.createNewAccount(accountDTO);
        account.setAccessLevelCollection(generateAccessLevels());
        account.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
        PreviousPassword previousPassword = new PreviousPassword();
        previousPassword.setPassword(account.getPassword());
        previousPassword.setAccount(account);
        account.getPreviousPasswordCollection().add(previousPassword);
        int callCounter = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("numberOfTransactionRepeat"));
        boolean rollback;
        do {
            try {
                accountManager.createAccount(account);
                rollback = accountManager.isLastTransactionRollback();
                callCounter--;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter > 0);
        if (!rollback) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendRegistrationEmail(account.getEmail(), account.getVeryficationToken());
        }
        if (callCounter == 0 && rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }
    @Override
    @PermitAll
    public Collection<AccessLevel> generateAccessLevels() throws AppBaseException {
        Collection<AccessLevel> accessLevels = new ArrayList<>();
        Properties properties =  ResourceBundles.loadProperties("config.user_roles.properties");

        Client client = new Client();
        client.setAccount(account);
        client.setAccessLevel(properties.getProperty("roleClient"));
        client.setActive(true);
        accessLevels.add(client);

        Manager manager = new Manager();
        manager.setAccount(account);
        manager.setAccessLevel(properties.getProperty("roleManager"));
        manager.setActive(false);
        accessLevels.add(manager);

        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setAccessLevel(properties.getProperty("roleAdmin"));
        admin.setActive(false);
        accessLevels.add(admin);

        return accessLevels;
    }
}