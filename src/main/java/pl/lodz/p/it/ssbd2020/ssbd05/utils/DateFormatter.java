package pl.lodz.p.it.ssbd2020.ssbd05.utils;



import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static final String WITH_SECONDS= "yyyy-MM-dd HH:mm:ss";
    public static final String WITHOUT_SECONDS= "yyyy-MM-dd HH:mm";
    static final int SECONDS_PER_MINUTE = 60;

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

    public static int getHours(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start,end);
        long minutes = duration.toMinutes()%SECONDS_PER_MINUTE;
        int numberOfHours = (int) duration.toHours();
        if(minutes>0)
            numberOfHours+=1;

        return numberOfHours;
    }
}
