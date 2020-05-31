package pl.lodz.p.it.ssbd2020.ssbd05.web.mor;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Getter
@Setter
@Named
@RequestScoped
public class AddExtraServiceController {

    private String name;
    private double price;
    private String description;
    private boolean active;

    public String goBack() {
        return "home";
    }

    public void addExtraService() {

    }
}
