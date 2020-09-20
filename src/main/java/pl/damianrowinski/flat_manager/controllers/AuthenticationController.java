package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damianrowinski.flat_manager.model.dtos.UserAddDTO;
import pl.damianrowinski.flat_manager.services.AuthenticationService;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public String getRegistration(Model model) {
        UserAddDTO userAddDTO = new UserAddDTO();
        model.addAttribute("userData", userAddDTO);
        return "/login/registration";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute UserAddDTO userData) {
        authenticationService.register(userData);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login/login";
    }

    @GetMapping("/logout-confirm")
    public String confirmLogout() {
        return "/login/logout-confirmation";
    }

}
