package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.exceptions.ObjectInRelationshipException;
import pl.damianrowinski.flat_manager.model.repositories.TenantRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    public void delete(Long tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id");
        if (optionalTenant.get().getRoom() != null)
            throw new ObjectInRelationshipException("Pokój ma najemcę, najpierw usuń najemcę, a później pokój");
        tenantRepository.delete(optionalTenant.get());
    }

    public boolean checkIfDeleteIsPossible(Long tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id");
        if (optionalTenant.get().getRoom() != null)
            throw new ObjectInRelationshipException("Pokój ma najemcę, najpierw usuń najemcę, a później pokój");
        return true;
    }
}
