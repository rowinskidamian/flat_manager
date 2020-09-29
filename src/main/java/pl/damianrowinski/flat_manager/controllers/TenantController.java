package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.model.dtos.room.RoomListDTO;
import pl.damianrowinski.flat_manager.model.dtos.tenant.TenantAddressDTO;
import pl.damianrowinski.flat_manager.model.dtos.tenant.TenantEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.tenant.TenantShowDTO;
import pl.damianrowinski.flat_manager.services.RoomService;
import pl.damianrowinski.flat_manager.services.TenantService;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;
import pl.damianrowinski.flat_manager.validation.groups.AddTenantGroup;
import pl.damianrowinski.flat_manager.validation.groups.AddressValidationGroup;
import pl.damianrowinski.flat_manager.validation.groups.EditTenantGroup;

import javax.validation.groups.Default;
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
        model.addAttribute("validAddress", "false");
        return "/tenant/form";
    }

    @PostMapping("/add")
    public String chooseValidation(String validAddress) {
        log.info("Validation with address: " + validAddress);
        if ("true".equals(validAddress)) {
            return "forward:/tenant/add/valid/address";
        } else {
            return "forward:/tenant/add/valid/no_address";
        }
    }

    @PostMapping("/add/valid/no_address")
    public String addTenant(@ModelAttribute("tenantData") @Validated({Default.class, AddTenantGroup.class})
                                        TenantEditDTO tenantToAdd, BindingResult result, Model model) {
        model.addAttribute("validAddress", "false");
        return tenantAddFormController(tenantToAdd, result, model);
    }

    @PostMapping("/add/valid/address")
    public String addTenantValidAddress(@ModelAttribute("tenantData") @Validated({Default.class, AddTenantGroup.class,
            AddressValidationGroup.class}) TenantEditDTO tenantToAdd, BindingResult result,
                                        Model model) {
        model.addAttribute("validAddress", "true");
        return tenantAddFormController(tenantToAdd, result, model);
    }

    private String tenantAddFormController(@ModelAttribute("tenantData") @Validated({Default.class, AddTenantGroup.class,
            AddressValidationGroup.class}) TenantEditDTO tenantToAdd, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Unable to save tenant:");
            log.error(tenantToAdd.toString());
            List<RoomListDTO> availableRoomsData = roomService.findAllAvailableRooms(LoggedUsername.get());
            model.addAttribute("tenantData", tenantToAdd);
            model.addAttribute("roomListData", availableRoomsData);
            return "/tenant/form";
        }
        tenantService.save(tenantToAdd);
        return "redirect:/tenant";
    }

    @GetMapping("/edit/{tenantId}")
    public String generateEditForm(@PathVariable Long tenantId, Model model) {
        TenantEditDTO tenantToEdit = tenantService.findById(tenantId);
        List<RoomListDTO> availableRoomsData = roomService.findAllAvailableRooms(LoggedUsername.get());
        model.addAttribute("tenantData", tenantToEdit);
        model.addAttribute("roomListData", availableRoomsData);
        String validAddress = tenantToEdit.getStreetNumber() != null ? "true" : "false";
        log.info("Valid address: " + validAddress);
        log.info("Object to edit: " + tenantToEdit.toString());
        model.addAttribute("validAddress", validAddress);
        return "/tenant/form_edit";
    }

    @PostMapping("/edit/{tenantId}")
    public String chooseValidationForEdit(@PathVariable Long tenantId, String validAddress) {
        log.info("Validation with address: " + validAddress);
        if ("true".equals(validAddress)) {
            return "forward:/tenant/edit/valid/address";
        } else {
            return "forward:/tenant/edit/valid/no_address";
        }
    }

    @PostMapping("/edit/valid/no_address")
    public String editTenant(@ModelAttribute("tenantData") @Validated({Default.class, EditTenantGroup.class})
                                         TenantEditDTO tenantToAdd, BindingResult result, Model model) {
        model.addAttribute("validAddress", "false");
        return tenantEditFormController(tenantToAdd, result, model);
    }

    @PostMapping("/edit/valid/address")
    public String editTenantValidAddress(@ModelAttribute("tenantData") @Validated({Default.class, EditTenantGroup.class,
            AddressValidationGroup.class}) TenantEditDTO tenantToAdd, BindingResult result,
                                         Model model) {
        model.addAttribute("validAddress", "true");
        return tenantEditFormController(tenantToAdd, result, model);
    }

    private String tenantEditFormController(@ModelAttribute("tenantData") @Validated({Default.class, EditTenantGroup.class,
            AddressValidationGroup.class}) TenantEditDTO tenantToEdit, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Nie udało się edytować najemcy:");
            log.error(tenantToEdit.toString());
            List<RoomListDTO> availableRoomsData = roomService.findAllAvailableRooms(LoggedUsername.get());
            model.addAttribute("tenantData", tenantToEdit);
            model.addAttribute("roomListData", availableRoomsData);
            return "/tenant/form_edit";
        }
        tenantService.save(tenantToEdit);
        return "redirect:/tenant";
    }

    @GetMapping("/address/{tenantId}")
    public String showTenantAddress(@PathVariable Long tenantId, Model model) {
        TenantAddressDTO tenantAddress = tenantService.findTenantAddress(tenantId);
        model.addAttribute("tenantAddressData", tenantAddress);
        return "/tenant/address";
    }

}
