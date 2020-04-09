package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.PreviousPassword;


@Stateless
public class PreviousPasswordFacade extends AbstractFacade<PreviousPassword> {

    @PersistenceContext(unitName = "ssbd05mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PreviousPasswordFacade() {
        super(PreviousPassword.class);
    }
    
}
