package pl.damianrowinski.flat_manager.module1_crud.listeners;

import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.BaseEntityOwner;
import pl.damianrowinski.flat_manager.app_common.exceptions.ForbiddenAccessException;
import pl.damianrowinski.flat_manager.app_common.utils.LoggedUsername;

import javax.persistence.PostLoad;
import javax.persistence.PreRemove;

@Component
public class BaseEntityOwnerListener {

    @PostLoad
    private void afterLoad(BaseEntityOwner baseEntityOwner) {
        String loggedUserName = baseEntityOwner.getLoggedUserName();
        if(!loggedUserName.equals(LoggedUsername.get())) throw new ForbiddenAccessException("Brak dostępu.");
    }

    @PreRemove
    private void preRemove(BaseEntityOwner baseEntityOwner) {
        String loggedUserName = baseEntityOwner.getLoggedUserName();
        if(!loggedUserName.equals(LoggedUsername.get())) throw new ForbiddenAccessException("Brak dostępu.");
    }
}
