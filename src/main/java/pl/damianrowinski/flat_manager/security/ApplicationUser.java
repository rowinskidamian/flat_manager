package pl.damianrowinski.flat_manager.security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ApplicationUser extends User {
    private final pl.damianrowinski.flat_manager.user.User user;
    public ApplicationUser(String userLogin, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.damianrowinski.flat_manager.user.User user) {
        super(userLogin, password, authorities);
        this.user = user;
    }
    public pl.damianrowinski.flat_manager.user.User getUser() {return user;}
}
