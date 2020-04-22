package pl.lodz.p.it.ssbd2020.ssbd05.mor.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class HallFacade extends AbstractFacade<Hall> {

    @PersistenceContext(unitName = "ssbd05morPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HallFacade() {
        super(Hall.class);
    }
    
}
