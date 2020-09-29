package pl.damianrowinski.flat_manager.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {

        String patternToAdd = "\\d\\d\\d\\d-\\d\\d-\\d\\d";
        boolean isPatternToAdd = Pattern.matches(source, patternToAdd);

        LocalDate parseOutcome;

        if (isPatternToAdd) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (source == null || source.equals("'") || source.equals(""))
                return LocalDate.parse("1111-11-11", formatter);
            parseOutcome = LocalDate.parse(source, formatter);
        } else {
            parseOutcome = LocalDate.parse(source, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        return parseOutcome;
    }
}
