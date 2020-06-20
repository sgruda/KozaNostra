package pl.lodz.p.it.ssbd2020.ssbd05.dto.mos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Klasa DTO zawierająca informacje o sali istniejącej w systemie.
 * Jej instancje są wykorzystywane w warstwie prezentacji.
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class HallDTO {

    private String name;
    private int capacity;
    private boolean active;
    private double area;
    private String description;
    private double price;
    private Collection<String> event_type = new ArrayList<>();
    private AddressDTO address;
}
