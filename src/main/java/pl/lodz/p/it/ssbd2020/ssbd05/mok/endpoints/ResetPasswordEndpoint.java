package pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.AccountMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mok.ForgotPasswordTokenMapper;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.ForgotPasswordTokenDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.ForgotPasswordToken;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.PreviousPassword;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mok.AccountPasswordAlreadyUsedException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.endpoints.interfaces.ResetPasswordEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.EmailSender;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.HashGenerator;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.security.PermitAll;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(TrackerInterceptor.class)
public class ResetPasswordEndpoint implements Serializable, ResetPasswordEndpointLocal {

    @Inject
    private AccountManager accountManager;
    private Account account;
    private ForgotPasswordToken forgotPasswordToken;

    @Override
    @PermitAll
    public void findByMail(String mail) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                account = accountManager.findByMail(mail);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    @Override
    @PermitAll
    public AccountDTO findByLogin(String login) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                account = accountManager.findByLogin(login);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return AccountMapper.INSTANCE.toAccountDTO(account);
    }

    @PermitAll
    private void deletePreviousToken() throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                for(ForgotPasswordToken token : accountManager.getAllTokens()) {
                    if(token.getAccount().getLogin().equals(account.getLogin()))
                        accountManager.deletePreviousToken(token);
                }
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    @Override
    @PermitAll
    public void resetPassword(String mail) throws AppBaseException {
        this.deletePreviousToken();

        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setAccount(account);
        forgotPasswordToken.setExpireDate(LocalDateTime.now().plusMinutes(15));

        String hash = HashGenerator.sha256(UUID.randomUUID().toString() + forgotPasswordToken.getExpireDate().toString());
        forgotPasswordToken.setHash(hash);

        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.createForgotPasswordToken(forgotPasswordToken);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (!rollback) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendPasswordResetEmail(mail, hash);
        }
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
    }

    @Override
    @PermitAll
    public ForgotPasswordTokenDTO findByHash(String hash) throws AppBaseException {
        int callCounter = 0;
        boolean rollback;
        do {
            try {
                forgotPasswordToken = accountManager.findTokenByHash(hash);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        return ForgotPasswordTokenMapper.INSTANCE.toTokenDTO(forgotPasswordToken);
    }

    @Override
    @PermitAll
    public void changeResettedPassword(AccountDTO accountDTO) throws AppBaseException {
        for (PreviousPassword psw: account.getPreviousPasswordCollection()){
            if(psw.getPassword().equals(HashGenerator.sha256(accountDTO.getPassword()))){
                throw new AccountPasswordAlreadyUsedException();
            }
        }
        account.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
        PreviousPassword previousPassword = new PreviousPassword();
        previousPassword.setAccount(account);
        previousPassword.setPassword(HashGenerator.sha256(accountDTO.getPassword()));
        account.getPreviousPasswordCollection().add(previousPassword);

        int callCounter = 0;
        boolean rollback;
        do {
            try {
                accountManager.setPasswordAfterReset(account);
                rollback = accountManager.isLastTransactionRollback();
                if(callCounter > 0)
                    log.info("Transaction is being repeated for " + callCounter + " time");
                callCounter++;
            } catch (EJBTransactionRolledbackException e) {
                log.warn("EJBTransactionRolledBack");
                rollback = true;
            }
        } while (rollback && callCounter < ResourceBundles.getTransactionRepeatLimit());
        if (rollback) {
            throw new ExceededTransactionRetriesException();
        }
        this.deletePreviousToken();
    }
}
