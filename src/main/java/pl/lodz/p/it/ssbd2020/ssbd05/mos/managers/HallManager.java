package pl.lodz.p.it.ssbd2020.ssbd05.mos.managers;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.abstraction.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Address;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.Hall;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mos.HallNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd05.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.facades.AddressFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.facades.EventTypesFacade;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.facades.HallFacade;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Klasa odpowiedzialna za operacje na obiektach encyjnych typu Hall
 */
@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
@LocalBean
@Interceptors(TrackerInterceptor.class)
public class HallManager extends AbstractManager implements SessionSynchronization {

    @Inject
    private HallFacade hallFacade;
    @Inject
    private AddressFacade addressFacade;
    @Inject
    private EventTypesFacade eventTypesFacade;

    /**
     * Metoda odpowiedzialna za dodawanie sali i opcjonalne dodawanie adresu
     *
     * @param hall Obiekt typu Hall
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("addHall")
    public void addHall(Hall hall) throws AppBaseException {
        Optional<Address> address = addressFacade.findByStreetAndNumberAndCity(
                hall.getAddress().getStreet(),
                hall.getAddress().getStreetNo(),
                hall.getAddress().getCity());
        if (address.isPresent()) {
            hall.setAddress(address.get());
        } else {
            Address newAddress = new Address();
            newAddress.setStreet(hall.getAddress().getStreet());
            newAddress.setStreetNo(hall.getAddress().getStreetNo());
            newAddress.setCity(hall.getAddress().getCity());
            addressFacade.create(newAddress);
            hall.setAddress(newAddress);
        }
        hallFacade.create(hall);
    }

    /**
     * Metoda odpowiedzialna za wyszukiwanie sali na podstawie jej nazwy
     *
     * @param name Nazwa sali
     * @return Obiekt typu Hall
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @PermitAll
    public Hall getHallByName(String name) throws AppBaseException {
        if (hallFacade.findByName(name).isPresent()) {
            return hallFacade.findByName(name).get();
        } else {
            throw new HallNotFoundException();
        }
    }

    /**
     * Metoda odpowiedzialna za pobieranie wszystkich sal
     *
     * @return kolekcja obiektów typu Hall
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @PermitAll
    public Collection<Hall> getAllHalls() throws AppBaseException {
        return hallFacade.findAll();
    }

    /**
     * Metoda odpowiedzialna za filtrowanie listy sal zgodnie z podanym ciągiem znaków
     *
     * @param hallFilter Ciąg znaków do filtrowania
     * @return Kolekcja obiektów typu Hall
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @PermitAll
    public Collection<Hall> getFilteredHalls(String hallFilter) throws AppBaseException {
        return hallFacade.filter(hallFilter);
    }

    /**
     * Metoda odpowiedzialna za edycję sali
     *
     * @param hall Obiekt typu Hall
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("editHall")
    public void editHall(Hall hall) throws AppBaseException {
        hallFacade.edit(hall);
    }

    /**
     * Metoda odpowiedzialna za pobieranie wszystkich typów imprez
     *
     * @return lista obiektów typu EventType
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getAllEventTypes")
    public List<EventType> getAllEventTypes() throws AppBaseException {
        return eventTypesFacade.findAll();
    }

    /**
     * Metoda odpowiedzialna za pobieranie wszystkich adresów
     *
     * @return lista obiektów typu Address
     * @throws AppBaseException podstawowy wyjątek aplikacyjny
     */
    @RolesAllowed("getAllAddresses")
    public List<Address> getAllAddresses() throws AppBaseException {
        return addressFacade.findAll();
    }

    @RolesAllowed("removeHall")
    public void removeHall(Hall hall) throws AppBaseException{
        throw new UnsupportedOperationException();
    }

    @RolesAllowed("changeHallActivity")
    public void changeActivity(Hall hall) throws AppBaseException{
        hallFacade.edit(hall);
    }
}
