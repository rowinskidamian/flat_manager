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
@Table(name = BaseEntityPersonalDetails.TABLE_NAME)
@MappedSuperclass
public abstract class BaseEntityPersonalDetails extends BaseEntityLoggedUser {

    final static String TABLE_NAME = "personal_details";

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;

}
