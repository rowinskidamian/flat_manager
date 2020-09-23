package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.model.dtos.PropertyEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.PropertyShowDTO;
import pl.damianrowinski.flat_manager.services.PropertyService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/property")
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping("/add")
    public String generateAddForm(Model model) {
        model.addAttribute("propertyData", new PropertyEditDTO());
        return "/property/form";
    }

    @PostMapping("/add")
    public String addProperty(@ModelAttribute("propertyData") PropertyEditDTO propertyData) {
        Property savedProperty = propertyService.save(propertyData);
        return "/property/show_saved/" + savedProperty.getId();
    }

    @RequestMapping("/show/{id}")
    public String showProperty(@PathVariable Long id, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(id);
        model.addAttribute("propertyData", propertyData);
        return "/property/show";
    }


}
