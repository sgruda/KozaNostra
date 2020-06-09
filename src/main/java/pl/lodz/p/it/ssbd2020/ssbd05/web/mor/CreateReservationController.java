package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ReservationDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.UnavailableDate;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.CreateReservationEndpointLocal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String hallName;


    public String goToReservationPage(String hallName){
        this.hallName = hallName;
        try{
            hallDTO = createReservationEndpointLocal.getHallByName(hallName);
        }catch(AppBaseException ex){
            log.severe(ex.getMessage());
        }
        System.out.println(this.hallName);
        return "toReservationPage";
    }

    @PostConstruct
    private void init() {
        try{
            extraServices = createReservationEndpointLocal.getAllExtraServices();
            unavailableDates = createReservationEndpointLocal.getUnavailableDates();
            eventTypes = createReservationEndpointLocal.getAllEventTypes();
        }catch(AppBaseException ex){
            log.severe(ex.getMessage());
        }

    }


}
