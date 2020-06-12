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
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.DatesOverlapException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.ReservationStatuses;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.CreateReservationEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.DateFormatter;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
@Log
@Data
public class CreateReservationController implements Serializable {

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

    private LocalDateTime today;

    private double totalPrice = 0;


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

    public double calculateTotalPrice() {
        Period period = DateFormatter.getPeriod(startDate, endDate);
        int rentedTime = period.getDays();
        long[] time = DateFormatter.getTime(startDate, endDate);
        if (time[2] > 0)
            rentedTime += 1;
        double totalPrice = hallDTO.getPrice() * rentedTime * numberOfGuests;
        for (ExtraServiceDTO ext : extraServices) {
            for (int i = 0; i < selectedExtraServices.size(); i++) {
                if (ext.getServiceName().equals(selectedExtraServices.get(i)))
                    totalPrice += ext.getPrice();
            }

        }
        this.totalPrice = totalPrice;
        return totalPrice;
    }

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
        reservationDTO.setReservationNumber(UUID.randomUUID().toString().replace("-", ""));
        boolean notGood = false;
        if (startDate.isAfter(endDate) || endDate.isBefore(startDate)) {
            ResourceBundles.emitErrorMessage(null, "page.createreservation.dates.error");
            notGood = true;
        } else {
            for (ScheduleEvent ev : eventModel.getEvents()) {
                if (ev.getEndDate().isAfter(startDate) && ev.getStartDate().isBefore(endDate)) {
                        ResourceBundles.emitErrorMessage(null, "error.createreservation.dates.overlap");
                        notGood = true;
                    }
                }
            }
        if(!notGood){
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

        eventModel = new DefaultScheduleModel();
        today = LocalDateTime.now();
        startDate = LocalDateTime.now();
        endDate = LocalDateTime.now();


        for (UnavailableDate unavailableDate : unavailableDates) {
            DefaultScheduleEvent event = DefaultScheduleEvent.builder().editable(false)
                    .title(ResourceBundles.getTranslatedText("page.createreservation.title"))
                    .startDate(unavailableDate.getStartDate())
                    .endDate(unavailableDate.getEndDate()).overlapAllowed(false)
                    .build();
            eventModel.addEvent(event);
        }


    }

    public String goBack() {
        return "goBack";
    }


}
