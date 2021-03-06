package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.damianrowinski.flat_manager.domain.model.dtos.user.UserListDTO;
import pl.damianrowinski.flat_manager.services.HomeService;
import pl.damianrowinski.flat_manager.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@SessionAttributes({"userFirstName"})
public class HomeController {

    private final UserService userService;
    private final HomeService homeService;

    @RequestMapping
    public String home(Principal principal, Model model) {
        if (principal != null) {
            String loggedUserName = principal.getName();
            UserListDTO loggedUser = userService.findByLoginPreview(loggedUserName);
            double currentBalance = homeService.getCurrentBalance();
            long noOfTotalRooms = homeService.findNoOfTotalRooms();
            long noOfRentedRooms = homeService.findNoOfRentedRooms();
            long noOfTotalTenants = homeService.findNoOfTotalTenants();
            long noOfTotalProperties = homeService.findNoOfTotalProperties();

            model.addAttribute("userFirstName", loggedUser.getFirstName());
            model.addAttribute("currentBalance", currentBalance);
            model.addAttribute("noOfRoomsTotal", noOfTotalRooms);
            model.addAttribute("noOfRoomsRented", noOfRentedRooms);
            model.addAttribute("noOfTenantsTotal", noOfTotalTenants);
            model.addAttribute("noOfPropertiesTotal", noOfTotalProperties);
        }
        return "home";
    }
}
