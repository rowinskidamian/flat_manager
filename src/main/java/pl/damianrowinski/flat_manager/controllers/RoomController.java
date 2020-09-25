package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.model.dtos.*;
import pl.damianrowinski.flat_manager.services.PropertyService;
import pl.damianrowinski.flat_manager.services.RoomService;
import pl.damianrowinski.flat_manager.services.TenantService;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

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
        RoomAddDTO roomData = new RoomAddDTO();
        addPropertyAndTenantsLists(model);
        model.addAttribute("roomData", roomData);
        return "/room/form";
    }

    private void addPropertyAndTenantsLists(Model model) {
        List<PropertyListDTO> userPropertiesList = propertyService.findAllByUserShowList(LoggedUsername.get());
        List<TenantListDTO> tenantList = tenantService.findAllWithoutRooms(LoggedUsername.get());
        model.addAttribute("propertyListData", userPropertiesList);
        model.addAttribute("tenantListData", tenantList);
    }

    @PostMapping("/add")
    public String addRoomToBase(@ModelAttribute("roomData") @Valid RoomAddDTO roomData, BindingResult result,
                                Model model) {
        if(result.hasErrors()) {
            addPropertyAndTenantsLists(model);
            model.addAttribute("roomData", roomData);
            return "/room/form";
        }
        roomService.save(roomData);
        return "redirect:/room";
    }

    @GetMapping("/delete_by_property/{roomId}")
    public String generateConfirmationPage(@PathVariable Long roomId, Model model) {
        RoomDeleteDTO roomDeleteData = roomService.findRoomToDelete(roomId);
        model.addAttribute("roomDeleteData", roomDeleteData);
        return "/room/delete-confirmation";
    }

    @PostMapping("/delete_by_property")
    public String deleteConfirmed(@ModelAttribute("roomDeleteData") RoomDeleteDTO roomDeleteData) {
        roomService.delete(roomDeleteData.getId());
        return "redirect:/room/edit_by_property/" + roomDeleteData.getPropertyId();
    }

    @GetMapping("/edit_by_property/{propertyId}")
    public String showProperty(@PathVariable Long propertyId, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(propertyId);
        model.addAttribute("propertyData", propertyData);

        RoomAddDTO roomData = new RoomAddDTO();
        model.addAttribute("roomData", roomData);
        return "/room/edit_by_property";
    }

    @PostMapping("/edit_by_property")
    public String addRoomByProperty(@ModelAttribute("roomData") @Valid RoomAddDTO roomData,
                                    BindingResult result, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(roomData.getPropertyId());
        model.addAttribute("propertyData", propertyData);

        if (result.hasErrors()) {
            return "/room/edit_by_property";
        }

        roomService.addNewToProperty(roomData);
        return "redirect:/room/edit_by_property/" + roomData.getPropertyId();
    }
}
