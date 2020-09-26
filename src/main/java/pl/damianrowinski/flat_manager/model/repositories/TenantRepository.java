package pl.damianrowinski.flat_manager.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.entities.Room;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant> findAllByLoggedUserName(String loggedUserName);

    List<Tenant> findAllByLoggedUserNameAndRoomIsNull(String loggedUserName);

}
