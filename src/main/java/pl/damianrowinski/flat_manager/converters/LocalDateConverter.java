package pl.damianrowinski.flat_manager.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
@Slf4j
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {

        log.info("Object to convert: " + source);

        String patternToAdd = "\\d\\d\\d\\d-\\d\\d-\\d\\d";
        String patternToEdit = "\\d\\d.\\d\\d.\\d\\d\\d\\d";
        boolean isPatternToAdd = Pattern.matches(source, patternToAdd);
        boolean isPatternToEdit = Pattern.matches(source, patternToEdit);

        LocalDate parseOutcome;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (source.equals("'") || source.equals(""))
            return LocalDate.parse("1111-11-11", formatter);

        parseOutcome = LocalDate.parse(source, formatter);

//        if (isPatternToAdd) {
//            parseOutcome = LocalDate.parse(source, formatter);
//        } else if (isPatternToEdit) {
//            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//            parseOutcome = LocalDate.parse(source, formatter);
//        } else {
//            throw new IllegalArgumentException("Wprowadzono datę w błędnym formacie.");
//        }

        log.info("Object converted: " + parseOutcome.toString());

        return parseOutcome;
    }
}
