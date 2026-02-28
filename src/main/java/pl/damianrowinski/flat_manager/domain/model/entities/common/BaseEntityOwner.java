package pl.damianrowinski.flat_manager.domain.model.entities.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.damianrowinski.flat_manager.listeners.BaseEntityOwnerListener;

import jakarta.persistence.*;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(BaseEntityOwnerListener.class)

public abstract class BaseEntityOwner extends BaseEntity {

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
