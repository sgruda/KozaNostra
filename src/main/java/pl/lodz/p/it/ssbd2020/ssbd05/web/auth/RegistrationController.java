package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import lombok.Data;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.*;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.web.mok.EmailController;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.Collection;

@Named
@Data
@RequestScoped
public class RegistrationController implements Serializable {

    @Inject
    private AccountFacade accountFacade;
    private String login;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private String emailAddress;


    public void register() {
            if (!checkIfEmailAlreadyExists(this.emailAddress)) {
                if (!checkIfLoginAlreadyExists(this.login)) {
                    if (password.equals(confirmPassword)) {
                        Account account = new Account();
                        account.setLogin(this.getLogin());
                        account.setPassword(sha256(password));
                        account.setFirstname(this.getFirstname());
                        account.setLastname(this.getLastname());
                        account.setEmail(this.getEmailAddress());
                        account.getAccessLevelCollection().addAll(generateAccessLevels(account));
                        this.getAccountFacade().create(account);
                        EmailController emailController = new EmailController();
                        emailController.sendRegistrationEmail(this.getEmailAddress(), account.getVeryficationToken(), this.getLogin());
                        FacesContext fc=FacesContext.getCurrentInstance();
                        fc.addMessage(null, new FacesMessage("Account created !"));
                        clear();
                    }
                }
            }
    }

    public boolean checkIfLoginAlreadyExists(String username) {
        Collection<Account> accounts = this.accountFacade.getAllAccounts();
        for (Account account : accounts) {
            if (account.getLogin().equals(username)) return true;
        }
        return false;
    }

    public boolean checkIfEmailAlreadyExists(String emailAddress) {
        Collection<Account> accounts = this.accountFacade.getAllAccounts();
        for (Account account : accounts) {
            if (account.getEmail().equals(emailAddress)) return true;
        }
        return false;
    }

    Collection<AccessLevel> generateAccessLevels(Account account) {
        Collection<AccessLevel> accessLevels = new ArrayDeque<>();
        Client client = new Client();
        client.setAccount(account);
        client.setAccessLevel("CLIENT");
        client.setActive(true);

        Manager manager = new Manager();
        manager.setAccount(account);
        manager.setAccessLevel("MANAGER");
        manager.setActive(false);

        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setAccessLevel("ADMIN");
        admin.setActive(false);

        accessLevels.add(client);
        accessLevels.add(manager);
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

    public void clear() {
        this.setLogin("");
        this.setPassword("");
        this.setConfirmPassword("");
        this.setEmailAddress("");
        this.setFirstname("");
        this.setLastname("");
    }
}
