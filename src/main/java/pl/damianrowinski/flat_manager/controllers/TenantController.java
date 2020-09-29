package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damianrowinski.flat_manager.model.dtos.RoomListDTO;
import pl.damianrowinski.flat_manager.model.dtos.TenantEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.TenantShowDTO;
import pl.damianrowinski.flat_manager.services.RoomService;
import pl.damianrowinski.flat_manager.services.TenantService;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tenant")
@RequiredArgsConstructor
@Slf4j

public class TenantController {

    private final TenantService tenantService;
    private final RoomService roomService;

    @RequestMapping
    public String redirectToList() {
        return "redirect:/tenant/list";
    }

    @RequestMapping("/list")
    public String generateList(Model model) {
        List<TenantShowDTO> tenantList = tenantService.findAllLoggedUser(LoggedUsername.get());
        model.addAttribute("tenantList", tenantList);
        return "/tenant/list";
    }

    @GetMapping("/add")
    public String generateAddForm(Model model) {
        TenantEditDTO tenantData = new TenantEditDTO();
        List<RoomListDTO> availableRoomsData = roomService.findAllAvailableRooms(LoggedUsername.get());
        model.addAttribute("tenantData", tenantData);
        model.addAttribute("roomListData", availableRoomsData);
        return "/tenant/form";
    }

    @PostMapping("/add")
    public String addTenantToBase(@ModelAttribute("tenantData") @Valid TenantEditDTO tenantToAdd, BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            log.error("Nie udało się zapisać najemcy:");
            log.error(tenantToAdd.toString());
            List<RoomListDTO> availableRoomsData = roomService.findAllAvailableRooms(LoggedUsername.get());
            model.addAttribute("tenantData", tenantToAdd);
            model.addAttribute("roomListData", availableRoomsData);
            return "/tenant/form";
        }
        tenantService.save(tenantToAdd);
        return "redirect:/tenant";
    }


}
