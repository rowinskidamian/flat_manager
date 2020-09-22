package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.config.Role;
import pl.damianrowinski.flat_manager.domain.entities.User;
import pl.damianrowinski.flat_manager.model.common.PersonalDetails;
import pl.damianrowinski.flat_manager.model.dtos.UserAddDTO;
import pl.damianrowinski.flat_manager.model.repositories.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserAddDTO userAddDTO) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userAddDTO.getPassword());
        user.setLogin(userAddDTO.getLogin());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setRole(Role.USER.toString());

        PersonalDetails personalDetails = user.getPersonalDetails();;

        personalDetails.setFirstName(userAddDTO.getFirstName());
        personalDetails.setLastName(userAddDTO.getLastName());
        personalDetails.setEmail(userAddDTO.getEmail());
        personalDetails.setCityName(userAddDTO.getCityName());
        personalDetails.setStreetName(userAddDTO.getStreetName());
        personalDetails.setStreetNumber(userAddDTO.getStreetNumber());
        personalDetails.setApartmentNumber(userAddDTO.getApartmentNumber());

        user.setPersonalDetails(personalDetails);

        log.info("Attempt to save user: " + user);
        log.info("User personal details: " + personalDetails);
        userRepository.save(user);
    }
}
