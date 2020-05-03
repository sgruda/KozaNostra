package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.PreviousPassword;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@LocalBean
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
