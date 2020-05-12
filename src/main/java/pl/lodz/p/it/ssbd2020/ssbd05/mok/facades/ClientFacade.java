package pl.lodz.p.it.ssbd2020.ssbd05.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd05.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mok.Client;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless(name = "ClientFacadeMOK")
@LocalBean
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "ssbd05mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }

    @Override
    //    @RolesAllowed()
    public void create(Client entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    //    @RolesAllowed()
    public void edit(Client entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    //    @RolesAllowed()
    public Optional<Client> find(Object id) {
        return super.find(id);
    }

    @Override
    //    @RolesAllowed()
    public List<Client> findAll() {
        return super.findAll();
    }
}
