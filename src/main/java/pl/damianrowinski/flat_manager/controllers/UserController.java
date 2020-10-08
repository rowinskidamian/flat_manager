package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damianrowinski.flat_manager.domain.model.dtos.user.UserShowDTO;
import pl.damianrowinski.flat_manager.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/details")
    public String userDetails(Principal principal, Model model){
        String userName = principal.getName();
        UserShowDTO userDataToShow = userService.findByLoginDetailed(userName);
        model.addAttribute("userData", userDataToShow);
        return "/user/details";
    }
}
