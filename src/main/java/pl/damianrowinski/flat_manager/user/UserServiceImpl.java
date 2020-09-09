package pl.damianrowinski.flat_manager.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.security.Role;
import pl.damianrowinski.flat_manager.security.RoleRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUserLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        userRepository.save(user);
    }
}
