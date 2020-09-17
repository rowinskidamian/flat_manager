package pl.damianrowinski.flat_manager.domain.entities;

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
