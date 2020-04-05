package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.PasswordHistory;


@Stateless
public class PasswordHistoryFacade extends AbstractFacade<PasswordHistory> {

    @PersistenceContext(unitName = "ssbd05mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PasswordHistoryFacade() {
        super(PasswordHistory.class);
    }
    
}
