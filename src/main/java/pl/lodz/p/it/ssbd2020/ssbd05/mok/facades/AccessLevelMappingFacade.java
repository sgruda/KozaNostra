package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mok.entities.AccessLevelMapping;


@Stateless
public class AccessLevelMappingFacade extends AbstractFacade<AccessLevelMapping> {

    @PersistenceContext(unitName = "ssbd05mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccessLevelMappingFacade() {
        super(AccessLevelMapping.class);
    }

}
