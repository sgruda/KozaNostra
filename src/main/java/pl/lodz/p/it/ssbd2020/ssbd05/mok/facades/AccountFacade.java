package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd05mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    public Optional<Collection<Account>> getAllAccounts(){
        return Optional.ofNullable(this.em.createNamedQuery("Account.findAll",Account.class).getResultList());
    }

    public Optional<Account> findByLogin(String username) {
        return Optional.ofNullable(this.em.createNamedQuery("Account.findByLogin", Account.class)
                .setParameter("login", username).getSingleResult());
    }

    @PermitAll
    public void create(Account account){
        try{
            super.create(account);
         }catch (Exception e){
            throw e;
        }
    }
    public void edit(Account account) {
        try{
            super.edit(account);
        }catch (Exception e){
            throw e;
        }
    }
}
