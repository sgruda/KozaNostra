package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
public class RegisterAccountEndpoint implements Serializable{

    @Inject
    private AccountManager accountManager;

    @Getter
    @Setter
    private Account account;

    @PermitAll
    public  void addNewAccount(AccountDTO accountDTO){
        account = AccountMapper.INSTANCE.createNewAccount(accountDTO);
        account.setAccessLevelCollection(generateAccessLevels());
        account.setPassword(sha256(accountDTO.getPassword()));
        accountManager.createAccount(account);
    }

    public Collection<AccessLevel> generateAccessLevels() {
        Collection<AccessLevel> accessLevels = new ArrayList<>();

        Client client = new Client();
        client.setAccount(account);
        client.setAccessLevel("CLIENT");
        client.setActive(true);
        accessLevels.add(client);

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

        return accessLevels;
    }

    private String sha256(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = new byte[0];
        if (digest != null) {
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        }
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
