package pl.damianrowinski.flat_manager.module1_crud.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.module1_crud.assemblers.UserAssembler;
import pl.damianrowinski.flat_manager.app_common.config.Role;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.User;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.user.UserAddDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.repositories.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAssembler userAssembler;

    public User register(UserAddDTO userAddDTO) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userAddDTO.getPassword());
        user.setLogin(userAddDTO.getLogin());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setRole(Role.USER.toString());

        userAssembler.setPersonalDetailsUserAdd(userAddDTO, user);
        userAssembler.setAddressUserAdd(userAddDTO, user);

        log.info("Attempt to save user: " + user);
        return userRepository.save(user);
    }

}
