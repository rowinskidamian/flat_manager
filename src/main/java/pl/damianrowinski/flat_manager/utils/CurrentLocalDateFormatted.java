package pl.damianrowinski.flat_manager.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrentLocalDateFormatted {

    public static LocalDate get() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-y");
        String currentDateFormatted = LocalDate.now().format(formatter);
        return LocalDate.parse(currentDateFormatted, formatter);
    }
}
