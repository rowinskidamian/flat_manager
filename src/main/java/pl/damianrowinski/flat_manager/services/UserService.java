package pl.damianrowinski.flat_manager.services;

import pl.damianrowinski.flat_manager.domain.entities.User;

public interface UserService {
    User findByUserLogin(String login);
    void saveUser(User user);
}
