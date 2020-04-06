package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.entities.ExtraService;


@Stateless
public class ExtraServiceFacade extends AbstractFacade<ExtraService> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExtraServiceFacade() {
        super(ExtraService.class);
    }
    
}
