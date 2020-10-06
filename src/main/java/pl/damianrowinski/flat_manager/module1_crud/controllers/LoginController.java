package pl.damianrowinski.flat_manager.module1_crud.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.user.UserAddDTO;
import pl.damianrowinski.flat_manager.module1_crud.services.AuthenticationService;
import pl.damianrowinski.flat_manager.module1_crud.validation.groups.AddressValidationGroup;

import javax.validation.Valid;
import javax.validation.groups.Default;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public String getRegistration(Model model) {
        model.addAttribute("userData", new UserAddDTO());
        model.addAttribute("validAddress", "false");
        return "/login/registration";
    }

    @PostMapping("/register")
    public String chooseValidation(String validAddress) {
        log.info("Validation with address: " + validAddress);
        if ("true".equals(validAddress)) {
            return "forward:/register/validation/address";
        } else {
            return "forward:/register/validation/no_address";
        }
    }

    @PostMapping("/register/validation/address")
    public String processValidationWithAddress(@ModelAttribute("userData") @Validated({Default.class,
            AddressValidationGroup.class}) UserAddDTO userData, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("validAddress", "true");
            return "/login/registration";
        }
        authenticationService.register(userData);
        return "redirect:/";
    }

    @PostMapping("register/validation/no_address")
    public String processRegistrationNoAddress(@ModelAttribute("userData") @Valid UserAddDTO userData,
                                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("validAddress", "false");
            return "/login/registration";
        }
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
