package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.UserAssembler;
import pl.damianrowinski.flat_manager.config.Role;
import pl.damianrowinski.flat_manager.domain.entities.User;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.model.dtos.user.UserAddDTO;
import pl.damianrowinski.flat_manager.model.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAssembler userAssembler;

    public void register(UserAddDTO userAddDTO) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userAddDTO.getPassword());
        user.setLogin(userAddDTO.getLogin());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setRole(Role.USER.toString());

        userAssembler.setPersonalDetailsUserAdd(userAddDTO, user);
        userAssembler. setAddressUserAdd(userAddDTO, user);

        log.info("Attempt to save user: " + user);
        userRepository.save(user);
    }

}
