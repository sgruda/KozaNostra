package pl.lodz.p.it.ssbd2020.ssbd05.dto.mor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class ExtraServiceDTO {

    private Long id;
    private String description;
    private double price;
    private String serviceName;
    private boolean active;
}
