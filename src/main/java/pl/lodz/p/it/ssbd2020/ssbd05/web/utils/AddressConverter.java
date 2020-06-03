package pl.lodz.p.it.ssbd2020.ssbd05.web.utils;

import pl.lodz.p.it.ssbd2020.ssbd05.dto.mos.AddressDTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("addressConverter")
public class AddressConverter implements Converter<AddressDTO> {

    @Override
    public AddressDTO getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(s.substring(0, s.indexOf(',')).replaceAll("\\d+", "").stripTrailing());
        addressDTO.setStreetNo(Integer.parseInt(s.replaceAll("\\D+", "")));
        addressDTO.setCity(s.substring(s.indexOf(',') + 1).stripLeading());
        return addressDTO;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, AddressDTO addressDTO) {
        return addressDTO.show();
    }
}
