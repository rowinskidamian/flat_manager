package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.config.Role;
import pl.damianrowinski.flat_manager.domain.entities.User;
import pl.damianrowinski.flat_manager.model.common.Address;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.model.dtos.user.UserAddDTO;
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

        PersonNameContact personalDetails = user.getNameContact();

        personalDetails.setFirstName(userAddDTO.getFirstName());
        personalDetails.setLastName(userAddDTO.getLastName());
        personalDetails.setEmail(userAddDTO.getEmail());

        Address address = user.getAddress();

        address.setCityName(userAddDTO.getCityName());
        address.setStreetName(userAddDTO.getStreetName());
        address.setStreetNumber(userAddDTO.getStreetNumber());
        address.setApartmentNumber(userAddDTO.getApartmentNumber());

        user.setNameContact(personalDetails);
        user.setAddress(address);

        log.info("Attempt to save user: " + user);
        userRepository.save(user);
    }
}
