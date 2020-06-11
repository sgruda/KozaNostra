package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.EventTypeDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
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

    private List<UnavailableDate> unavailableDates;

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
            unavailableDates = editReservationEndpointLocal.getUnavailableDates(hallDTO.getName());
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
        eventModel = new DefaultScheduleModel();
        for (UnavailableDate unavailableDate: unavailableDates){
            DefaultScheduleEvent event = DefaultScheduleEvent.builder()
                    .title("Rezerwacja")
                    .startDate(unavailableDate.getStartDate())
                    .endDate(unavailableDate.getEndDate())
                    .build();
            eventModel.addEvent(event);
        }
    }

    public void editReservation(){

        log.severe("herb " + reservationDTO.getEventTypeName());
        try {
            reservationDTO.setEventTypeName(eventTypeName);
            reservationDTO.setExtraServiceCollection(extraServicesNames);

            editReservationEndpointLocal.editReservation(reservationDTO);
            ResourceBundles.emitErrorMessageWithFlash(null, "page.client.editreservation.success");
            log.severe("poszlo kontorller");
            for (String extraServicesName : extraServicesNames) log.severe(extraServicesName + "\n");
        } catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessageWithFlash(null, "error.client.editreservation.optimisticlock");
        } catch (AppBaseException appBaseException) {
            ResourceBundles.emitErrorMessageWithFlash(null,appBaseException.getMessage());
            log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());
        }
    }

    //Do kalendarza i wyświetlania okienek czasowych
    //start
    private ScheduleModel scheduleModel;

    private ScheduleModel eventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private boolean datesRenderd = false;


    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();
        for (ScheduleEvent ev :scheduleModel.getEvents()){
            if(event.getStartDate().isAfter(ev.getStartDate()) && event.getEndDate().isBefore(ev.getEndDate())){
                if(ev.isAllDay()){
                    datesRenderd = false;
                }else{
                    datesRenderd = true;
                }
            }
        }
    }

    public void addEvent() {
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);

        event = new DefaultScheduleEvent();
    }



    public String goBack(){
        return "goToDetails";
    }
}
