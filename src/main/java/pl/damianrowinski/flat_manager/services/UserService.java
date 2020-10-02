package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.UserAssembler;
import pl.damianrowinski.flat_manager.domain.entities.User;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.model.dtos.user.UserListDTO;
import pl.damianrowinski.flat_manager.model.dtos.user.UserShowDTO;
import pl.damianrowinski.flat_manager.model.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    public UserListDTO findByLoginPreview(String login) {
        User user = findUserByLogin(login);
        return userAssembler.getDataListFrom(user);
    }

    public UserShowDTO findByLoginDetailed(String login) {
        User user = findUserByLogin(login);
        return userAssembler.getDataShowFrom(user);
    }

    private User findUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findAllByLogin(login);
        if (optionalUser.isEmpty()) throw new ElementNotFoundException("Nie znaleziono użytkownika.");
        return optionalUser.get();
    }
}
