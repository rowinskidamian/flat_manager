package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.model.entities.Tenant;
import pl.damianrowinski.flat_manager.domain.model.entities.User;
import pl.damianrowinski.flat_manager.domain.repositories.TenantRepository;
import pl.damianrowinski.flat_manager.domain.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ValidationService {
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;

    public boolean checkUniqueEmail(String emailToCheck) {
        Optional<Tenant> optionalTenant = tenantRepository.findFirstByPersonalDetailsEmail(emailToCheck);
        if (optionalTenant.isPresent()) return false;
        Optional<User> optionalUser = userRepository.findFirstByNameContactEmail(emailToCheck);
        if (optionalUser.isPresent()) return false;

        return true;
    }
}
