package pl.damianrowinski.flat_manager.owner;

import pl.damianrowinski.flat_manager.personal_details.PersonalDetails;
import pl.damianrowinski.flat_manager.property.Property;

import javax.persistence.*;
import java.util.List;

@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private PersonalDetails personalDetails;

    @OneToMany(mappedBy = "owner")
    private List<Property> propertyList;

}
