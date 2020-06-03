package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;

import java.util.Collection;
import java.util.stream.Collectors;

public class EventTypeMapper {

    public static String toEventTypeString(EventType eventType) {
        return eventType.getTypeName();
    }

    public static Collection<String> toEventTypeStringCollection(Collection<EventType> eventTypeCollection) {
        return eventTypeCollection.stream()
                .map(EventTypeMapper::toEventTypeString)
                .collect(Collectors.toList());
    }
}