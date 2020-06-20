package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Klasa narzędziowa odpowiadająca za operacje na datach.
 */
public class DateFormatter {

    /**
     * Stała reprezentująca format daty.
     */
    public static final String WITH_SECONDS= "yyyy-MM-dd HH:mm:ss";
    private static final int SECONDS_PER_MINUTE = 60;

    /**
     * Metoda odpowiedzialna za wyświetlanie obiektów klasy LocalDateTime.
     *
     * @param date Obiekt klasy LocalDateTime.
     * @return Ciąg znaków reprezentujący datę.
     */
    public static String formatDate(LocalDateTime date) {
        if (date == null) {
            return "";
        } else {
            return date.format(DateTimeFormatter.ofPattern(WITH_SECONDS));
        }
    }

    /**
     * Metoda odpowiedzialna za konwersję ciągów reprezentujących datę na obiekt klasy LocalDateTime.
     *
     * @param date Ciąg znaków reprezentujący datę.
     * @return Obiekt klasy LocalDateTime.
     */
    public static LocalDateTime stringToLocalDateTime(String date) {
        return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(WITH_SECONDS));
    }

    /**
     * Metoda pobierająca ilość godzin pomiędzy dwoma datami.
     *
     * @param start Data początkowa.
     * @param end   Data końcowa.
     * @return Ilość godzin zaokrąglona w górę.
     */
    public static int getHours(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start,end);
        long minutes = duration.toMinutes()%SECONDS_PER_MINUTE;
        int numberOfHours = (int) duration.toHours();
        if(minutes>0)
            numberOfHours+=1;

        return numberOfHours;
    }
}
