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
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/property")
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;

    @RequestMapping
    public String welcomePage() {
        return "redirect:/property/list";
    }

    @RequestMapping("/list")
    public String showList(Model model) {
        List<PropertyShowDTO> propertyList = propertyService.findAllByUser(LoggedUsername.get());
        model.addAttribute("propertyList", propertyList);
        return "/property/list";
    }

    @GetMapping("/add")
    public String generateAddForm(Model model) {
        model.addAttribute("propertyData", new PropertyEditDTO());
        return "/property/form";
    }

    @PostMapping("/add")
    public String addProperty(@ModelAttribute("propertyData") PropertyEditDTO propertyData) {
        Property savedProperty = propertyService.save(propertyData);
        return "redirect:/property/show/" + savedProperty.getId();
    }

    @RequestMapping("/show/{id}")
    public String showProperty(@PathVariable Long id, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(id);
        model.addAttribute("propertyData", propertyData);
        return "/property/show";
    }


}
