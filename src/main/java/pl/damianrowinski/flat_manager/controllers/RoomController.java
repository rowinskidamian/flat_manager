package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.model.dtos.PropertyShowDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomAddDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomDeleteDTO;
import pl.damianrowinski.flat_manager.services.PropertyService;
import pl.damianrowinski.flat_manager.services.RoomService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;
    private final PropertyService propertyService;

    @GetMapping("/delete_by_property/{roomId}")
    public String generateConfirmationPage(@PathVariable Long roomId, Model model) {
        RoomDeleteDTO roomDeleteData = roomService.findRoomToDelete(roomId);
        model.addAttribute("roomDeleteData", roomDeleteData);
        return "/room/delete-confirmation";
    }

    @PostMapping("/delete_by_property")
    public String deleteConfirmed(@ModelAttribute("roomDeleteData") RoomDeleteDTO roomDeleteData) {
        log.info("roomData: " + roomDeleteData.toString());

        roomService.delete(roomDeleteData.getId());
        return "redirect:/room/edit_by_property/" + roomDeleteData.getPropertyId();
    }

    @GetMapping("/edit_by_property/{propertyId}")
    public String showProperty(@PathVariable Long propertyId, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(propertyId);
        model.addAttribute("propertyData", propertyData);

        RoomAddDTO rooomData = new RoomAddDTO();
        model.addAttribute("roomData", rooomData);
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
