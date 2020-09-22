package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@ToString

public abstract class BaseEntityLoggedUser extends BaseEntity {

    @Column(name="logged_user_name", nullable = false)
    private String loggedUserName;

    @PrePersist
    private void addLoggedUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof UserDetails) {
                loggedUserName = ((UserDetails) principal).getUsername();
            } else {
                loggedUserName = principal.toString();
            }
        }
    }

}
