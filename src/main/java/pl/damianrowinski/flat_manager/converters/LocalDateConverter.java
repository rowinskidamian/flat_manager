package pl.damianrowinski.flat_manager.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (source == null || source.equals("'") || source.equals(""))
            return LocalDate.parse("1111-11-11", formatter);

        return LocalDate.parse(source, formatter);
    }
}
