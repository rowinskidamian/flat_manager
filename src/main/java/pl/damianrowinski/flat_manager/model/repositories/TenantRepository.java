package pl.damianrowinski.flat_manager.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    @Query("SELECT t FROM Tenant t WHERE t.loggedUserName = ?1 AND t.room IS NULL")
    List<Tenant> findByLoggedUserWithNoRoom(String LoggedUserName);

}
