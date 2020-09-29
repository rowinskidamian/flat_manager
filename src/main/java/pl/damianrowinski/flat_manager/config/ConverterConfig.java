package pl.damianrowinski.flat_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.damianrowinski.flat_manager.converters.LocalDateConverter;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(getLocalDateConverter());
    }
    @Bean
    public LocalDateConverter getLocalDateConverter() {
        return new LocalDateConverter();
    }
}
