package pl.damianrowinski.flat_manager.module1_crud.domain.model.common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class PersonNameContact {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
