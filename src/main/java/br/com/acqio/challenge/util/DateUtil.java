package br.com.acqio.challenge.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy H:mm:ss";

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private static final String TIME_PATTERN = "H:mm:ss";

    public static LocalDateTime convertToDateTime(String date, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return LocalDateTime.parse(date + " " + time, formatter);
    }

    public static String getDateFromLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return dateTime.format(formatter);
    }

    public static String getTimeFromLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return dateTime.format(formatter);
    }
}
