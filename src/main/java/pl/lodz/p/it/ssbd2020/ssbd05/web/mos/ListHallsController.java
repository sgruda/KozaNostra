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

/**
 * Kontroler odpowiedzialny za wyświetlenie wszystkich sal dostępnych w systemie
 */
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

    /**
     * Metoda wykonywana po stworzeniu instancji klasy ListHallsController. Pobiera wszystkie sale z bazy
     */
    @PostConstruct
    public void init() {
        try {
            halls = listHallsEndpoint.getAllHalls();
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    /**
     * Metoda odpowiedzialna za pobranie wybranej sali z bazy i przekierowanie na stronę z jej szczegółami
     *
     * @param name nazwa sali
     * @return ciąg znaków przekierowujący na stronę ze szczegółami sali
     */
    public String selectHall(String name) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedHallName", name);
        return "hallDetails";
    }

    /**
     * Metoda odpowiedzialna za filtrowanie listy sal zgoodnie z przekazanym ciągiem znaków
     */
    public void filterHalls() {
        try {
            halls = listHallsEndpoint.getFilteredHalls(hallFilter);
        } catch (AppBaseException e) {
            log.warning(e.getClass().toString() + " " + e.getMessage());
            ResourceBundles.emitErrorMessageWithFlash(null, e.getMessage());
        }
    }

    /**
     * Metoda informująca użytkownika o aktywności sali
     *
     * @param hall Obiekt typu HallDTO
     * @return Zinternacjonalizowany ciąg znaków
     */
    public String getActiveString(HallDTO hall) {
        if (hall.isActive()) {
            return ResourceBundles.getTranslatedText("page.hall.active");
        } else {
            return ResourceBundles.getTranslatedText("page.hall.inactive");
        }
    }
}
