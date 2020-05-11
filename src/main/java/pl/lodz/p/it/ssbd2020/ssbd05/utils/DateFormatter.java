package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static final String WITH_SECONDS= "yyyy-MM-dd HH:mm:ss";
    public static final String WITHOUT_SECONDS= "yyyy-MM-dd HH:mm";

    public static String formatDate(LocalDateTime date) {
        if (date == null) {
            return "";
        } else {
            return date.format(DateTimeFormatter.ofPattern(WITH_SECONDS));
        }
    }
}
