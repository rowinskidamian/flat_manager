package pl.damianrowinski.flat_manager.domain.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Transactional
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

    @ManyToOne
    @NotNull
    private User user;


}
