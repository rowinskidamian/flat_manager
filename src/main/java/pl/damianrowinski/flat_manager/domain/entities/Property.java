package pl.damianrowinski.flat_manager.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Owner owner;

    @OneToMany
    private List<Room> rooms;

    private Double rent;

    private LocalDate paymentDate;

    private LocalDate contractBeginningDate;

    private LocalDate contractEndingDate;


}
