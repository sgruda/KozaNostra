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
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    private LocalDateTime today;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @PostConstruct
    public void init() {
        try {
            reservationDTO = editReservationEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            hallDTO = editReservationEndpointLocal.getHallByName(reservationDTO.getHallName());
            extraServices = editReservationEndpointLocal.getAllExtraServices();
            eventTypeName = reservationDTO.getEventTypeName();
            unavailableDates = editReservationEndpointLocal.getUnavailableDates(hallDTO.getName());
            extraServicesNames = new ArrayList<>();
            for (ExtraServiceDTO extraServiceDTO : extraServices) {
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
        today = LocalDateTime.now();
        startDate = LocalDateTime.now();
        endDate = LocalDateTime.now();


        for (UnavailableDate unavailableDate : unavailableDates) {
            log.severe("dataaa " + unavailableDate.getStartDate() + unavailableDate.getEndDate());
                if(!reservationDTO.getStartDate().equals(DateFormatter.formatDate(unavailableDate.getStartDate()))) {
                    DefaultScheduleEvent event = DefaultScheduleEvent.builder().editable(false)
                            .title(ResourceBundles.getTranslatedText("page.createreservation.title"))
                            .startDate(unavailableDate.getStartDate())
                            .endDate(unavailableDate.getEndDate()).overlapAllowed(false)
                            .build();
                    log.severe("dodanie evebtu");
                    eventModel.addEvent(event);
                }
        }

    }

    @RolesAllowed("editReservationClient")
    public void editReservation() {

        log.severe("herb " + reservationDTO.getEventTypeName());

        reservationDTO.setEventTypeName(eventTypeName);
        reservationDTO.setExtraServiceCollection(extraServicesNames);
        reservationDTO.setStartDate(DateFormatter.formatDate(startDate));
        reservationDTO.setEndDate(DateFormatter.formatDate(endDate));
        boolean notGood = false;
        if (startDate.isAfter(endDate) || endDate.isBefore(startDate)) {
            ResourceBundles.emitErrorMessage(null, "page.createreservation.dates.error");
            notGood = true;
        } else {
            for (ScheduleEvent ev : eventModel.getEvents()) {
                if (ev.getEndDate().isAfter(startDate) && ev.getStartDate().isBefore(endDate)) {
                    ResourceBundles.emitErrorMessageWithFlash(null, "error.createreservation.dates.overlap");
                    notGood = true;
                }
            }
        }
        if (!notGood) {
            try {
                editReservationEndpointLocal.editReservation(reservationDTO);
                ResourceBundles.emitMessageWithFlash(null, "page.client.editreservation.success");
                for (String extraServicesName : extraServicesNames) log.severe(extraServicesName + "\n");
            } catch (AppOptimisticLockException ex) {
                log.severe(ex.getMessage() + ", " + LocalDateTime.now());
                ResourceBundles.emitErrorMessageWithFlash(null, "error.client.editreservation.optimisticlock");
            } catch (ValidationException e) {
                ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
                log.severe(e.getMessage() + ", " + LocalDateTime.now());
            } catch (AppBaseException appBaseException) {
                ResourceBundles.emitErrorMessageWithFlash(null, "error.client.editreservation.optimisticlock");
                log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());

            }
        }
    }



    //Do kalendarza i wyświetlania okienek czasowych
    //start
    private ScheduleModel scheduleModel;

    private ScheduleModel eventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private boolean datesRenderd = false;


    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject()).overlapAllowed(false).editable(false).build();
    }

    public void addEvent(){
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);

        event = new DefaultScheduleEvent();
        startDate = event.getStartDate();
        endDate = event.getEndDate();

    }

    public String goBack(){
        return "goToDetails";
    }
}
