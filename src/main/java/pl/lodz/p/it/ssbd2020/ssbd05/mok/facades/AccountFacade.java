package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Account;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

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

    public Collection<Account> getAllAccounts(){
        try{
            return this.em.createNamedQuery("Account.findAll",Account.class).getResultList();
        }catch(Exception e){
            throw e;
        }
    }
    public Account findByLogin(String username) {
        TypedQuery<Account> query =  this.em.createNamedQuery("Account.findByLogin", Account.class);
        query.setParameter("login", username);
        try{
            return query.getSingleResult();
        }catch(Exception e){
            throw e;
        }
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
