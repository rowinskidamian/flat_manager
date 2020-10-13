package pl.damianrowinski.flat_manager.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentLocalDateTimeFormatted {

    public static LocalDateTime get() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-y HH:mm");
        String currentDateFormatted = LocalDateTime.now().format(formatter);
        return LocalDateTime.parse(currentDateFormatted, formatter);
    }
}
