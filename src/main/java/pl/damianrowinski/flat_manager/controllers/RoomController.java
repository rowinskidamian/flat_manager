package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.model.dtos.PropertyShowDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomAddDTO;
import pl.damianrowinski.flat_manager.services.PropertyService;
import pl.damianrowinski.flat_manager.services.RoomService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;
    private final PropertyService propertyService;

    @GetMapping("/add")
    public String generateAddingRoom(Model model) {
        model.addAttribute("roomData", new RoomAddDTO());
        return "/room/form";
    }
    @PostMapping("/add")
    public String addRoom(@ModelAttribute("roomData") RoomAddDTO roomData) {
        roomService.save(roomData);
        return "/room/list";
    }

    @PostMapping("/add_by_property")
    public String addRoomByProperty(Double rent, Long propertyId) {
        roomService.addNewToProperty(rent, propertyId);
        return "redirect:/room/edit_by_property/" + propertyId;
    }

    @RequestMapping("/edit_by_property/{propertyId}")
    public String showProperty(@PathVariable Long propertyId, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(propertyId);
        model.addAttribute("propertyData", propertyData);
        return "/room/edit_by_property";
    }
}
