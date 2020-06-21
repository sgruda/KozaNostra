package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;


import lombok.Data;
import lombok.extern.java.Log;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.ValidationException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.DateOverlapException;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Kontroler odpowiedzialny za edycję złożonej rezerwacji przez użytkownika z poziomem dostępu Klient
 */
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

    private List<String> selectedExtraServices;

    private HallDTO hallDTO;

    private String eventTypeName;

    private LocalDateTime today;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


    /**
     * Wczytanie danych dotyczących wybranej rezerwacji
     */
    @PostConstruct
    public void init() {
        try {
            reservationDTO = editReservationEndpointLocal.getReservationByNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedReservationNumber").toString());
            hallDTO = editReservationEndpointLocal.getHallByName(reservationDTO.getHallName());
            extraServices = editReservationEndpointLocal.getAllExtraServices();
            selectedExtraServices = new ArrayList<>(reservationDTO.getExtraServiceCollection());
            eventTypeName = reservationDTO.getEventTypeName();
            unavailableDates = editReservationEndpointLocal.getUnavailableDates(hallDTO.getName());
            extraServicesNames = new ArrayList<>();
            for (ExtraServiceDTO extraServiceDTO : extraServices) {
                extraServicesNames.add(extraServiceDTO.getServiceName());
            }
            eventTypes = (List<String>) hallDTO.getEvent_type();
        } catch (AppBaseException appBaseException) {
            appBaseException.printStackTrace();
        }
        eventModel = new DefaultScheduleModel();
        today = LocalDateTime.now();
        startDate = DateFormatter.stringToLocalDateTime(reservationDTO.getStartDate());
        endDate = DateFormatter.stringToLocalDateTime(reservationDTO.getEndDate());


        for (UnavailableDate unavailableDate : unavailableDates) {
                if(!reservationDTO.getStartDate().equals(DateFormatter.formatDate(unavailableDate.getStartDate()))) {
                    DefaultScheduleEvent event = DefaultScheduleEvent.builder().editable(false)
                            .title(ResourceBundles.getTranslatedText("page.editreservation.title"))
                            .startDate(unavailableDate.getStartDate())
                            .endDate(unavailableDate.getEndDate()).overlapAllowed(false)
                            .build();
                    eventModel.addEvent(event);
                }
        }

    }

    /**
     * Edycja rezerwacji
     */
    public void editReservation() {

        reservationDTO.setEventTypeName(eventTypeName);
        reservationDTO.setExtraServiceCollection(selectedExtraServices);
        reservationDTO.setStartDate(DateFormatter.formatDate(startDate));
        reservationDTO.setEndDate(DateFormatter.formatDate(endDate));
        log.severe(endDate + "sss " + startDate);
        boolean areDatesValid = false;
        if (startDate.isAfter(endDate)) {
            ResourceBundles.emitErrorMessageWithFlash(null, "page.editreservation.dates.error");
            areDatesValid = true;
        } else {
            for (ScheduleEvent ev : eventModel.getEvents()) {
                if (ev.getEndDate().isAfter(startDate) && ev.getStartDate().isBefore(endDate)) {
                    ResourceBundles.emitErrorMessageWithFlash(null, "error.editreservation.dates.overlap");
                    areDatesValid = true;
                }
            }
        }
        if (!areDatesValid) {
            try {
                editReservationEndpointLocal.editReservation(reservationDTO);
                ResourceBundles.emitMessageWithFlash(null, "page.client.editreservation.success");
            }catch (DateOverlapException e) {
                ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
                log.severe(e.getMessage() + ", " + LocalDateTime.now());
            } catch (AppOptimisticLockException ex) {
                log.severe(ex.getMessage() + ", " + LocalDateTime.now());
                ResourceBundles.emitErrorMessageWithFlash(null, "error.client.editreservation.optimisticlock");
            } catch (ValidationException e) {
                ResourceBundles.emitErrorMessageByPlainText(null, e.getMessage());
                log.severe(e.getMessage() + ", " + LocalDateTime.now());
            } catch (AppBaseException appBaseException) {
                ResourceBundles.emitErrorMessageWithFlash(null, "error.default");
                log.severe(appBaseException.getMessage() + ", " + LocalDateTime.now());

            }
        }
    }

    
    private ScheduleModel scheduleModel;

    private ScheduleModel eventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private boolean datesRenderd = true;

    /**
     * Wyświetl nazwę typu imprezy
     *
     * @param eventTypeName Nazwa typu imprezy
     * @return Zinternacjonalizowany ciąg znaków
     */

    public String translateExtraService(String eventTypeName) {
        return ResourceBundles.getTranslatedText(eventTypeName);
    }

    /**
     * Wróć na poprzednią stronę
     *
     * @return Ciąg znaków przenoszący na stronę ze szczegółami wybranej rezerwacji
     */
    public String goBack(){
        return "goToDetails";
    }

    public double calculateTotalPrice() {
        double price = 0;
        for (ExtraServiceDTO ext : extraServices) {
            for (int i = 0; i < selectedExtraServices.size(); i++) {
                if (ext.getServiceName().equals(selectedExtraServices.get(i)))
                    price += ext.getPrice();
            }
        }
        if(startDate.isAfter(endDate)){
            price = 0;
            reservationDTO.setTotalPrice(price);
            ResourceBundles.emitErrorMessage(null, "page.createreservation.dates.error");
        }else{
            Long numberOfGuests = reservationDTO.getGuestsNumber();
            price = this.editReservationEndpointLocal.calculateTotalPrice(startDate, endDate, hallDTO.getPrice(), Long.valueOf(numberOfGuests), price);
            reservationDTO.setTotalPrice(price);
        }
        return price;
    }
}
