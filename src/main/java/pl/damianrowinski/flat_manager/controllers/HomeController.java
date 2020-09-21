package pl.damianrowinski.flat_manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(Principal principal, Model model) {

        if (principal != null) {
            String loggedUserName = principal.getName();
            model.addAttribute("userLogin", loggedUserName);
        }

        return "home";
    }
}
