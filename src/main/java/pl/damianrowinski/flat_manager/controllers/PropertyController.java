package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.model.dtos.PropertyAddDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomAddDTO;
import pl.damianrowinski.flat_manager.services.PropertyService;
import pl.damianrowinski.flat_manager.services.RoomService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/property")
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping("/add")
    public String generateAddForm(Model model) {
        model.addAttribute("propertyData", new PropertyAddDTO());
        return "/property/form";
    }

    @PostMapping("/add")
    public String addProperty(@ModelAttribute("propertyData") PropertyAddDTO propertyData) {
        Property savedProperty = propertyService.save(propertyData);
        return "/property/show_saved/" + savedProperty.getId();
    }

    @RequestMapping("/show/{id}")
    public String showProperty(@PathVariable Long id) {
        propertyService.

    }


}
