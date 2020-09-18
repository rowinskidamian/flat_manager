package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Getter
@Setter
@Transactional

public class Owner extends BaseEntityPersonalDetails {

    @OneToMany(mappedBy = "owner")
    private List<Property> propertyList;

}
