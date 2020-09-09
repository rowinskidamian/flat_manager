package pl.damianrowinski.flat_manager.admin;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.damianrowinski.flat_manager.security.ApplicationUser;
import pl.damianrowinski.flat_manager.user.User;

@Controller
public class AdminController {

    @GetMapping("/admin")
    @ResponseBody
    public String admin(@AuthenticationPrincipal ApplicationUser loggedUser) {
        User entityUser = loggedUser.getUser();
        return "Hello " + entityUser.getLogin();
    }
}
