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

        String patternForEdit = "\\d\\d[.]\\d\\d[.]\\d{4}";
        String patternForAdd = "\\d{4}-\\d\\d-\\d\\d";
        boolean isPatternForEdit = Pattern.matches(patternForEdit, source);
        boolean isPatternForAdd = Pattern.matches(patternForAdd, source);

        log.info("Is convert for edit: " + isPatternForEdit);
        log.info("Is convert for add: " + isPatternForAdd);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (source.equals("'") || source.equals(""))
            return LocalDate.parse("1111-11-11", formatter);

        LocalDate parseOutcome;

        if (isPatternForEdit) {
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        } else if (isPatternForAdd) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }

        parseOutcome = LocalDate.parse(source, formatter);

        log.info("Object converted: " + parseOutcome.toString());

        return parseOutcome;
    }
}
