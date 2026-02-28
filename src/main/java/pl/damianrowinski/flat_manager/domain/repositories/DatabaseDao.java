package pl.damianrowinski.flat_manager.domain.repositories;


import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DatabaseDao {
    @PersistenceContext
    EntityManager entityManager;

    public void clearDatabase() {
        List<String> sqlQueries = List.of("DELETE FROM payments",
                "DELETE FROM rooms",
                "DELETE FROM tenants",
                "DELETE FROM properties",
                "DELETE FROM payment_balance");

        sqlQueries.forEach(query -> {
            Query nativeQuery = entityManager.createNativeQuery(query);
            nativeQuery.executeUpdate();
        });


    }
}
