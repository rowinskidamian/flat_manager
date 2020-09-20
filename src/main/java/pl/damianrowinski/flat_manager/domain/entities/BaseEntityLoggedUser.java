package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@ToString

public abstract class BaseEntityLoggedUser extends BaseEntity {

    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseEntityLoggedUser that = (BaseEntityLoggedUser) o;
        return Objects.equals(loggedUserName, that.loggedUserName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loggedUserName);
    }
}
