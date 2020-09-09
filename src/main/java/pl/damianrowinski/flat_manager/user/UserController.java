package pl.damianrowinski.flat_manager.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/create-user")
    @ResponseBody
    public String createUser() {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        userService.saveUser(user);
        return "admin";
    }

}
