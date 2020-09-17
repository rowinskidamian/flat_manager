package pl.damianrowinski.flat_manager.domain.entities;

import javax.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Double rent;

    @ManyToOne
    private Property property;

    @OneToOne
    private Tenant tenant;
}
