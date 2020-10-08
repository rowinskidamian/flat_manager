package pl.damianrowinski.flat_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.flat_manager.domain.model.entities.Room;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByPropertyId(Long id);

    List<Room> findAllByLoggedUserNameOrderByPropertyWorkingNameAsc(String loggedUsername);

    List<Room> findAllByLoggedUserNameAndTenantIsNull(String loggedUserName);

    Optional<Room> findFirstByTenantId(Long tenantId);

}
