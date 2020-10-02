package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.damianrowinski.flat_manager.model.dtos.user.UserListDTO;
import pl.damianrowinski.flat_manager.services.AuthenticationService;

import java.security.Principal;

@Controller
@RequestMapping("/")
@SessionAttributes({"userFirstName", "userLogin"})
@RequiredArgsConstructor
public class HomeController {

    private final AuthenticationService authenticationService;

    @RequestMapping
    public String home(Principal principal, Model model) {

        if (principal != null) {
            String loggedUserName = principal.getName();
            UserListDTO loggedUser = authenticationService.findByLogin(loggedUserName);
            model.addAttribute("userLogin", loggedUserName);
            model.addAttribute("userFirstName", loggedUser.getFirstName());
        }
        return "home";
    }
}
