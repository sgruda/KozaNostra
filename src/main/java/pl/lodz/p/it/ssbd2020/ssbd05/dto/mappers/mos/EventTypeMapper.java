package pl.lodz.p.it.ssbd2020.ssbd05.dto.mappers.mos;

import pl.lodz.p.it.ssbd2020.ssbd05.entities.mos.EventType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Klasa konwertująca pomiędzy obiektami klas EventType a ich nazwami.
 */
public class EventTypeMapper {

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy EventType na ciąg znaków będący jego nazwą.
     *
     * @param eventType Obiekt klasy EventType.
     * @return Nazwa typu imprezy.
     */
    public static String toEventTypeString(EventType eventType) {
        return eventType.getTypeName();
    }

    /**
     * Metoda odpowiedzialna za konwersję kolekcji obiektów typu EventType na kolekcję ich nazw.
     *
     * @param eventTypeCollection Kolekcja obiektów typu EventType.
     * @return Kolekcja nazw typów imprez.
     */
    public static Collection<String> toEventTypeStringCollection(Collection<EventType> eventTypeCollection) {
        return eventTypeCollection.stream()
                .map(EventTypeMapper::toEventTypeString)
                .collect(Collectors.toList());
    }
}