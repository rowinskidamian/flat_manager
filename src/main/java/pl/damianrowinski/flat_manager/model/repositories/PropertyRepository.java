package pl.damianrowinski.flat_manager.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.entities.Property;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAllByLoggedUserName(String loggedUserName);

}
