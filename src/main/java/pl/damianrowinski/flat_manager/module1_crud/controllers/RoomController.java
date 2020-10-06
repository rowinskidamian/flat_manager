package pl.damianrowinski.flat_manager.module1_crud.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.property.PropertyListDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.property.PropertyShowDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.room.*;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.tenant.TenantListDTO;
import pl.damianrowinski.flat_manager.module1_crud.services.PropertyService;
import pl.damianrowinski.flat_manager.module1_crud.services.RoomService;
import pl.damianrowinski.flat_manager.module1_crud.services.TenantService;
import pl.damianrowinski.flat_manager.app_common.utils.LoggedUsername;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;
    private final PropertyService propertyService;
    private final TenantService tenantService;

    @RequestMapping
    public String redirectToList() {
        return "redirect:/room/list";
    }

    @RequestMapping("/list")
    public String showList(Model model) {
        List<RoomShowDTO> roomList = roomService.findAllByUser(LoggedUsername.get());
        model.addAttribute("roomList", roomList);
        return "/room/list";
    }

    @GetMapping("/add")
    public String generateAddingForm(Model model) {
        RoomEditDTO roomData = new RoomEditDTO();
        addPropertyAndTenantsLists(model);
        model.addAttribute("roomData", roomData);
        return "/room/form";
    }

    @PostMapping("/add")
    public String addRoomToBase(@ModelAttribute("roomData") @Valid RoomEditDTO roomData, BindingResult result,
                                Model model) {
        if(result.hasErrors()) {
            addPropertyAndTenantsLists(model);
            model.addAttribute("roomData", roomData);
            return "/room/form";
        }
        roomService.save(roomData);
        return "redirect:/room";
    }

    @GetMapping("/edit/{roomId}")
    public String generateEditForm(@PathVariable Long roomId, Model model) {
        RoomEditDTO roomData = roomService.findRoomToEdit(roomId);
        addPropertyAndTenantsLists(model);
        model.addAttribute("roomData", roomData);
        return "/room/form";
    }

    @PostMapping("/edit/{roomId}")
    public String editRoom(@ModelAttribute("roomData") @Valid RoomEditDTO roomData, BindingResult result, Model model) {
        if(result.hasErrors()) {
            addPropertyAndTenantsLists(model);
            model.addAttribute("roomData", roomData);
            return "room/form";
        }
        roomService.save(roomData);
        return "redirect:/room";
    }

    private void addPropertyAndTenantsLists(Model model) {
        List<PropertyListDTO> userPropertiesList = propertyService.findAllByUserShowList(LoggedUsername.get());
        List<TenantListDTO> tenantList = tenantService.findAllWithoutRooms(LoggedUsername.get());
        model.addAttribute("propertyListData", userPropertiesList);
        model.addAttribute("tenantListData", tenantList);
    }

    @GetMapping("/delete/{roomId}")
    public String generateDeletePage(@PathVariable Long roomId, Model model) {
        RoomDeleteDTO roomDeleteData = roomService.findRoomToDelete(roomId);
        model.addAttribute("roomDeleteData", roomDeleteData);
        return "/room/delete_confirm";
    }

    @PostMapping("/delete")
    public String deleteRoom(@ModelAttribute("roomDeleteData") RoomDeleteDTO roomDeleteData) {
        roomService.delete(roomDeleteData.getId());
        return "redirect:/room";
    }

    @GetMapping("/delete_from_property/{roomId}")
    public String generateConfirmationPage(@PathVariable Long roomId, Model model) {
        RoomDeleteDTO roomDeleteData = roomService.findRoomToDelete(roomId);
        model.addAttribute("roomDeleteData", roomDeleteData);
        return "/room/delete_from_prop_conf";
    }

    @PostMapping("/delete_from_property")
    public String deleteConfirmed(@ModelAttribute("roomDeleteData") RoomDeleteDTO roomDeleteData) {
        roomService.delete(roomDeleteData.getId());
        return "redirect:/room/edit_in_property/" + roomDeleteData.getPropertyId();
    }

    @GetMapping("/edit_in_property/{propertyId}")
    public String showProperty(@PathVariable Long propertyId, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(propertyId);
        model.addAttribute("propertyData", propertyData);

        RoomEditDTO roomData = new RoomEditDTO();
        model.addAttribute("roomData", roomData);
        return "/room/edit_in_property";
    }

    @PostMapping("/edit_in_property")
    public String addRoomByProperty(@ModelAttribute("roomData") @Valid RoomEditDTO roomData,
                                    BindingResult result, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(roomData.getPropertyId());
        model.addAttribute("propertyData", propertyData);

        if (result.hasErrors()) {
            return "/room/edit_in_property";
        }

        roomService.addNewToProperty(roomData);
        return "redirect:/room/edit_in_property/" + roomData.getPropertyId();
    }

}
