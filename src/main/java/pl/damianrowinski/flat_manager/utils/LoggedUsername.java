package pl.damianrowinski.flat_manager.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.damianrowinski.flat_manager.exceptions.FrobiddenAccessException;

public abstract class LoggedUsername {

    public static String get() {
        String loggedUserName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof UserDetails) {
                loggedUserName = ((UserDetails) principal).getUsername();
            } else {
                loggedUserName = principal.toString();
            }
        } else throw new FrobiddenAccessException("Brak zalogowanego użytkownika.");

        return loggedUserName;
    }

}
