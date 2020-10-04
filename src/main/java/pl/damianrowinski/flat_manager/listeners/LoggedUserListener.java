package pl.damianrowinski.flat_manager.listeners;

import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.entities.BaseEntityLoggedUser;
import pl.damianrowinski.flat_manager.exceptions.ForbiddenAccessException;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.persistence.PostLoad;
import javax.persistence.PreRemove;

@Component
public class LoggedUserListener {

    @PostLoad
    private void afterLoad(BaseEntityLoggedUser baseEntityLoggedUser) {
        String loggedUserName = baseEntityLoggedUser.getLoggedUserName();
        if(!loggedUserName.equals(LoggedUsername.get())) throw new ForbiddenAccessException("Brak dostępu.");
    }

    @PreRemove
    private void preRemove(BaseEntityLoggedUser baseEntityLoggedUser) {
        String loggedUserName = baseEntityLoggedUser.getLoggedUserName();
        if(!loggedUserName.equals(LoggedUsername.get())) throw new ForbiddenAccessException("Brak dostępu.");
    }
}
