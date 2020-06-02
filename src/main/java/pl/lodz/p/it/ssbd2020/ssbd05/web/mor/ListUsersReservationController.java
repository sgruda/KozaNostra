package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mok.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.entities.mor.Reservation;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.ListUserReservationsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;


@Log
@Named
@ViewScoped
public class ListUsersReservationController implements Serializable {


    private Properties eventTypeProperties;
    private Properties statusProperties;

    @Inject
    ListUserReservationsEndpointLocal userReservationsEndpointLocal;

    @Getter
    @Setter
    List<ReservationDTO> usersReservations;

    public boolean isStatusSubmitted(String status){
        return status.equalsIgnoreCase((statusProperties.getProperty("statusSubmitted")));
    }

    @PostConstruct
    private void init() {
        try {
            this.eventTypeProperties = new Properties();
            this.statusProperties = new Properties();
            usersReservations = userReservationsEndpointLocal.getAllUsersReservations(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            this.eventTypeProperties = ResourceBundles.loadProperties("config.event_type.properties");
            this.statusProperties = ResourceBundles.loadProperties("config.status.properties");
        } catch (AppBaseException e) {
            log.severe(e.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }



}
