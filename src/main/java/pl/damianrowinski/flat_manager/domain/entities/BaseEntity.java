package pl.damianrowinski.flat_manager.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString

public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @PrePersist
    public void addCreatedDate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void updatedDate() {
        createdDate = LocalDateTime.now();
    }



}
