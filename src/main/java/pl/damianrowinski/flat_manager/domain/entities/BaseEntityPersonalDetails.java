package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@Transactional

@Inheritance(strategy = InheritanceType.JOINED)
public class BaseEntityPersonalDetails extends BaseEntityLoggedUser {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;

}
