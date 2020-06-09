package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReservationEndpointLocal;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Log
@Named
@ViewScoped
public class EditReservationController implements Serializable {

    @Inject
    private EditReservationEndpointLocal editReservationEndpointLocal;

    @Getter
    @Setter
    private ReservationDTO reservationDTO;

    @Getter
    private List<String> eventTypes;

    @PostConstruct
    public void init(){
        try {
            reservationDTO = editReservationEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            eventTypes = new ArrayList<>();
            eventTypes = editReservationEndpointLocal.getEventTypeForHall(reservationDTO.getHallName());
        } catch (AppBaseException appBaseException) {
            appBaseException.printStackTrace();
        }
    }
}
