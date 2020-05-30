package pl.lodz.p.it.ssbd2020.ssbd05.dto.mos;

import lombok.Data;

//to dto jest niepotrzebne
//równie dobrze można konwertować EventType na Stringa, bo identyfikujemy je po typeName, ale
//nie usuwałem bo korzysta z niego CreateReservationEndpoint i EditReservationEndpoint
public @Data class EventTypeDTO {

    private Long id;
    private String typeName;
}
