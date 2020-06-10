package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;


import lombok.Data;
import lombok.extern.java.Log;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ClientDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.CreateReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named
@Log
@Data
public class CreateReservationController {

    @Inject
    private CreateReservationEndpointLocal createReservationEndpointLocal;


    private List<UnavailableDate> unavailableDates;

    private List<ExtraServiceDTO> extraServices;

    private List<String> eventTypes;

    private HallDTO hallDTO;

    private ReservationDTO reservationDTO;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> selectedExtraServices = new ArrayList<>();
    private String eventTypeName;
    private Integer numberOfGuests;
    private ClientDTO clientDTO;

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











    //koniec
    public void createReservation() {
        reservationDTO = new ReservationDTO();
        clientDTO = new ClientDTO();
        clientDTO.setLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        reservationDTO.setStatusName(ReservationStatuses.submitted.toString());
        reservationDTO.setClientDTO(clientDTO);
        reservationDTO.setEndDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(endDate));
        reservationDTO.setStartDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(startDate));
        reservationDTO.setEventTypeName(eventTypeName);
        reservationDTO.setExtraServiceCollection(selectedExtraServices);
        reservationDTO.setHallName(hallDTO.getName());
        reservationDTO.setGuestsNumber(Long.valueOf(numberOfGuests));
        reservationDTO.setTotalPrice(calculateTotalPrice());
        reservationDTO.setReservationNumber( UUID.randomUUID().toString().replace("-", ""));
        try {
            createReservationEndpointLocal.createReservation(reservationDTO);
            ResourceBundles.emitMessageWithFlash(null, "page.createreservation.success");
    } catch (ValidationException e) {
        ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
        log.severe(e.getMessage() + ", " + LocalDateTime.now());
    } catch (AppBaseException e) {
        ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        log.severe(e.getMessage() + ", " + LocalDateTime.now());
    }
}

    private double calculateTotalPrice() {
        double totalPrice = hallDTO.getPrice() * numberOfGuests;
        for (ExtraServiceDTO ext : this.extraServices) {
            for (int i = 0; i < selectedExtraServices.size(); i++) {
                if (ext.getServiceName().equalsIgnoreCase(selectedExtraServices.get(i))) {
                    totalPrice += extraServices.get(i).getPrice();
                }
            }
        }
        return totalPrice;
    }


    @PostConstruct
    private void init() {
        String selectedHallName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedHallName");
        try {
            this.hallDTO = createReservationEndpointLocal.getHallByName(selectedHallName);
            this.extraServices = createReservationEndpointLocal.getAllExtraServices();
            this.unavailableDates = createReservationEndpointLocal.getUnavailableDates(selectedHallName);
            this.eventTypes = (List<String>) hallDTO.getEvent_type();
        } catch (AppBaseException ex) {
            log.severe(ex.getMessage());
        }

        //Tutaj jeszcze kalendarzyk
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
    public String goBack() {
        return "goBack";
    }


}
