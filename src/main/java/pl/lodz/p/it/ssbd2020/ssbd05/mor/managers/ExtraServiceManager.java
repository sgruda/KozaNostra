package pl.lodz.p.it.ssbd2020.ssbd05.mor.managers;

import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.ExtraService;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ExtraServiceNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.facades.ExtraServiceFacade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Klasa odpowiedzialna za operacje na obiektach encyjnych typu ExtraService
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class ExtraServiceManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private ExtraServiceFacade extraServiceFacade;


    /**
     * Pobierz wszystkie usługi dodatkowe
     *
     * @return lista wszystkich usług dodatkowych
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getAllExtraServices")
    public List<ExtraService> getAllExtraServices() throws AppBaseException {
        return extraServiceFacade.findAll();
    }

    /**
     * Metoda odpowiedzialna za utworzenie obiektu typu ExtraService
     *
     * @param extraService obiekt typu ExtraService
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("addExtraService")
    public void addExtraService(ExtraService extraService) throws AppBaseException {
        extraServiceFacade.create(extraService);
    }

    /**
     * Pobierz usługę dodatkową o podanej nazwie
     *
     * @param name nazwa usługi dodatkowej do pobrania
     * @return Obiekt usługi dodatkowej
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getExtraServiceByName")
    public ExtraService getExtraServiceByName(String name) throws AppBaseException {
        if(extraServiceFacade.findByName(name).isPresent())
            return extraServiceFacade.findByName(name).get();
        else throw new ExtraServiceNotFoundException("error.extraservice.not.found");
    }

    /**
     * Metoda odpowiedzialna za zmianę aktywności wybranej usługi dodatkowej
     *
     * @param extraService Obiekt typu ExtraService
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("changeExtraServiceActivity")
    public void changeActivity(ExtraService extraService) throws AppBaseException {
        extraService.setActive(!extraService.isActive());
        extraServiceFacade.edit(extraService);
    }
    /**
     * Edytuj usługę dodatkową
     *
     * @param extraService obiekt usługi dodatkowej podlegający edycji
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("editExtraService")
    public void editExtraService(ExtraService extraService) throws AppBaseException{
        extraServiceFacade.edit(extraService);
    }
}
