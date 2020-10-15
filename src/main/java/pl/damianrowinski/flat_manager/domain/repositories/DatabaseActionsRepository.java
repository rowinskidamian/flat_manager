package pl.damianrowinski.flat_manager.domain.repositories;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public interface DatabaseActionsRepository {

    void addProperty1();
    void addProperty2();
    void deleteProperties();

}
