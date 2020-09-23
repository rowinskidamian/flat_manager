package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damianrowinski.flat_manager.model.dtos.RoomAddDTO;
import pl.damianrowinski.flat_manager.services.RoomService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;

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

    @GetMapping("/add_by_property")
    public String generateFormByProperty(Model model) {
        model.addAttribute("roomData", new RoomAddDTO());
        return "/room/form_by_property";
    }

    @PostMapping("/add_by_property")
    public String addRoomByProperty(@ModelAttribute("roomData") RoomAddDTO roomData) {
        roomService.save(roomData);
        Long propertyId = roomData.getProperty().getId();
        return "/property/show/" + propertyId;
    }
}
