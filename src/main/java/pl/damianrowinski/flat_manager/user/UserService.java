package pl.damianrowinski.flat_manager.user;

public interface UserService {
    User findByUserLogin(String login);
    void saveUser(User user);
}
