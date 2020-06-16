package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static final String WITH_SECONDS= "yyyy-MM-dd HH:mm:ss";
    public static final String WITHOUT_SECONDS= "yyyy-MM-dd HH:mm";
    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    public static String formatDate(LocalDateTime date) {
        if (date == null) {
            return "";
        } else {
            return date.format(DateTimeFormatter.ofPattern(WITH_SECONDS));
        }
    }

    public static LocalDateTime stringToLocalDateTime(String date){
        return LocalDateTime.parse(date,DateTimeFormatter.ofPattern(WITH_SECONDS));
    }

    public static Period getPeriod(LocalDateTime dob, LocalDateTime now) {
        return Period.between(dob.toLocalDate(), now.toLocalDate());
    }

    public static long[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }
}
