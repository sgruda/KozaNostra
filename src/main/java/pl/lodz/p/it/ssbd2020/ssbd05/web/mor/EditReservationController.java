package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;


import lombok.Data;
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
@Data
public class EditReservationController implements Serializable {

    @Inject
    private EditReservationEndpointLocal editReservationEndpointLocal;

    private ReservationDTO reservationDTO;

    private List<String> eventTypes;

    private List<ExtraServiceDTO> extraServices;

    private List<String> extraServicesNames;

    private HallDTO hallDTO;

    private String eventTypeName;

    @PostConstruct
    public void init(){
        try {
            reservationDTO = editReservationEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            hallDTO = editReservationEndpointLocal.getHallByName(reservationDTO.getHallName());
            extraServices = editReservationEndpointLocal.getAllExtraServices();
            eventTypeName = reservationDTO.getEventTypeName();
            extraServicesNames = new ArrayList<>();
            for(ExtraServiceDTO extraServiceDTO: extraServices){
                extraServicesNames.add(extraServiceDTO.getServiceName());
                log.severe(extraServiceDTO.getServiceName());
            }
            log.severe(eventTypeName);
            log.severe("herb init " + reservationDTO.getEventTypeName());
            eventTypes = (List<String>) hallDTO.getEvent_type();
        } catch (AppBaseException appBaseException) {
            appBaseException.printStackTrace();
        }
    }

    public void editReservation(){

        log.severe("herb " + reservationDTO.getEventTypeName());
        try {
            log.severe("herb2 "+reservationDTO.getEventTypeName());
            log.severe("herb2.5 "+eventTypeName);
            reservationDTO.setEventTypeName(eventTypeName);
            log.severe("herb3 "+reservationDTO.getEventTypeName());
            editReservationEndpointLocal.editReservation(reservationDTO);
            log.severe("poszlo kontorller");
        } catch (AppBaseException appBaseException) {
            ResourceBundles.emitErrorMessageWithFlash(null,appBaseException.getMessage());
        }
    }

    public String goBack(){
        return "goToDetails";
    }
}
