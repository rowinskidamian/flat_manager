package pl.damianrowinski.flat_manager.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.services.DatabaseResetService;

@Component
@RequiredArgsConstructor
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private final DatabaseResetService databaseResetService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        databaseResetService.deleteAllForever();
        databaseResetService.generateRandomizedDatabase();

    }

}