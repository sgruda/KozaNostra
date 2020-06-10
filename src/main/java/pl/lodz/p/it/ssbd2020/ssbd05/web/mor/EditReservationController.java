package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

    @Getter
    @Setter
    private List<ExtraServiceDTO> extraServices;
    @Getter
    @Setter
    private String eventTypeName;
    @Getter
    @Setter
    private List<String> extraServicesNames;
    @Getter
    private HallDTO hallDTO;

    @PostConstruct
    public void init(){
        try {
            reservationDTO = editReservationEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            hallDTO = editReservationEndpointLocal.getHallByName(reservationDTO.getHallName());
            extraServices = editReservationEndpointLocal.getAllExtraServices();
            extraServicesNames = new ArrayList<>();
            for(ExtraServiceDTO extraServiceDTO: extraServices){
                extraServicesNames.add(extraServiceDTO.getServiceName());
                log.severe(extraServiceDTO.getServiceName());
            }
            log.severe(extraServicesNames.get(0).toString());
            log.severe(extraServicesNames.get(1).toString());
            eventTypes = (List<String>) hallDTO.getEvent_type();
        } catch (AppBaseException appBaseException) {
            appBaseException.printStackTrace();
        }
    }

    public void editReservation(){
        reservationDTO.setEventTypeName(eventTypeName);
        try {
            log.severe("kontroller poczatek");
            editReservationEndpointLocal.editReservation(reservationDTO);
            log.severe("poszlo kontorller");
        } catch (AppBaseException appBaseException) {
            ResourceBundles.emitErrorMessageWithFlash(null,appBaseException.getMessage());
        }
    }
}
