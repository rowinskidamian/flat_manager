package pl.damianrowinski.flat_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.model.entities.Property;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAllByLoggedUserName(String loggedUserName);

    @Query(value = "SELECT COUNT(r.id) FROM Room r WHERE r.loggedUserName = ?1")
    long findnoOfPropertiesForUser(String loggedUserName);

}
