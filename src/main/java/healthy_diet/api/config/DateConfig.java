package healthy_diet.api.config;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateConfig {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ZonedDateTime getDateNow() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public static String getDateNowString() {
        ZonedDateTime date = ZonedDateTime.now();
        return formatter.format(date);
    }


    public static String getDateString(ZonedDateTime z) {
        return z.format(formatter);
    }

    public static ZonedDateTime getZonedDateTimeFromDateStringDateDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        ZonedDateTime dateTime = ZonedDateTime.parse(dateString + " 00:00:00 UTC", formatter);
        return dateTime;
    }
}
