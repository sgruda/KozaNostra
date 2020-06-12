package pl.lodz.p.it.ssbd2020.ssbd05.web.mos;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.HallDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.mos.endpoints.interfaces.ListHallsEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Log
@Named
@ViewScoped
public class ListHallsController implements Serializable {

    @Inject
    private ListHallsEndpointLocal listHallsEndpoint;

    @Getter
    @Setter
    private List<HallDTO> halls;
    @Getter
    @Setter
    private String hallFilter;

    @PostConstruct
    public void init() {
        try {
            halls = listHallsEndpoint.getAllHalls();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    public String selectHall(String name) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedHallName", name);
        return "hallDetails";
    }

    public void filterHalls() {
        try {
            halls = listHallsEndpoint.getFilteredHalls(hallFilter);
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }
}
