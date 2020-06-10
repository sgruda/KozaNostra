package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2020.ssbd05.dto.mor.ExtraServiceDTO;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.DatabaseQueryException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.io.database.ExceededTransactionRetriesException;
import pl.lodz.p.it.ssbd2020.ssbd05.exceptions.mor.ExtraServiceAlreadyExistsException;
import pl.lodz.p.it.ssbd2020.ssbd05.mor.endpoints.interfaces.EditExtraServiceEndpointLocal;
import pl.lodz.p.it.ssbd2020.ssbd05.utils.ResourceBundles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;

/**
 * Kontroler wykorzystywany przy edycji usługi dodatkowej.
 */
@Named
@Log
@RequestScoped
public class EditExtraServiceController {

    @Inject
    private EditExtraServiceEndpointLocal editExtraServiceEndpointLocal;

    @Getter
    @Setter
    private ExtraServiceDTO extraServiceDTO;

    /**
     * Metoda odpowiedzialna za edycje usługi dodatkowej.
     */
    public void editExtraService(){
        try{
            editExtraServiceEndpointLocal.editExtraService(extraServiceDTO);
        }catch (AppOptimisticLockException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (ExceededTransactionRetriesException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        } catch (DatabaseQueryException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (DatabaseConnectionException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }catch (AppBaseException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
    }

    /**
     * Metoda inicjalizująca, pobiera obiekt DTO edytowanej usługi dodatkowej
     */
    @PostConstruct
    public void init(){
        String selectedExtraServiceName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedExtraService");
        try {
            this.extraServiceDTO = editExtraServiceEndpointLocal.getExtraServiceByName(selectedExtraServiceName);

        } catch (AppBaseException ex) {
            log.severe(ex.getMessage() + ", " + LocalDateTime.now());
            ResourceBundles.emitErrorMessage(null, ex.getMessage());
        }
    }

    /**
     * Metoda odpowiedzialna za przenoszenie na listę usług dodatkowych
     *
     * @return string
     */
    public String goBack() {
        return "listExtraServices";
    }



}
