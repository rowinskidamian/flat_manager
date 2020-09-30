package pl.damianrowinski.flat_manager.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.entities.BaseEntityLoggedUser;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.persistence.PostLoad;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggedUserListener {

    @PostLoad
    private void afterLoad(BaseEntityLoggedUser baseEntityLoggedUser) {
        String loggedUserName = baseEntityLoggedUser.getLoggedUserName();
        if(!loggedUserName.equals(LoggedUsername.get())) throw new ElementNotFoundException("Brak dostępu.");
    }
}
