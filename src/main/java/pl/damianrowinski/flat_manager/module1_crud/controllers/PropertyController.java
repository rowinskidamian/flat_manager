package pl.damianrowinski.flat_manager.module1_crud.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Property;
import pl.damianrowinski.flat_manager.app_common.exceptions.ForbiddenAccessException;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.property.PropertyDeleteDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.property.PropertyEditDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.property.PropertyShowDTO;
import pl.damianrowinski.flat_manager.module1_crud.services.PropertyService;
import pl.damianrowinski.flat_manager.app_common.utils.LoggedUsername;

import javax.validation.Valid;
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
    public String addProperty(@ModelAttribute("propertyData") @Valid PropertyEditDTO propertyData,
                              BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("propertyData", propertyData);
            return "/property/form";
        }

        Property savedProperty = propertyService.save(propertyData);
        return "redirect:/property/show/" + savedProperty.getId();
    }

    @GetMapping("/delete/{propertyId}")
    public String generateConfirmationPage(@PathVariable Long propertyId, Model model) {
        PropertyDeleteDTO propertyToDelete = propertyService.findPropertyToDelete(propertyId);
        model.addAttribute("propertyDeleteData", propertyToDelete);
        return "/property/delete-confirmation";
    }

    @PostMapping("/delete/{propertyId}")
    public String deleteConfirmed(@ModelAttribute("propertyDeleteData") PropertyDeleteDTO propertyDeleteData) {
        propertyService.delete(propertyDeleteData);
        return "redirect:/property";
    }


    @GetMapping("/edit/{propertyId}")
    public String generateEditForm(@PathVariable Long propertyId, Model model) {
        PropertyEditDTO propertyData = propertyService.findToEditById(propertyId);
        log.info("Property to edit: " + propertyData);
        model.addAttribute("propertyData", propertyData);
        return "/property/form";
    }

    @PostMapping("/edit/{propertyId}")
    public String saveChanges(@PathVariable Long propertyId, @ModelAttribute("propertyData") @Valid PropertyEditDTO
            propertyData, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("propertyData", propertyData);
            return "/property/form";
        }

        if(!propertyId.equals(propertyData.getId()))
            throw new ForbiddenAccessException("Id obiektu z bazy i edytowanego nie zgadzają się.");

        propertyService.save(propertyData);
        return "redirect:/property/show/" + propertyId;
    }

    @RequestMapping("/show/{id}")
    public String showProperty(@PathVariable Long id, Model model) {
        PropertyShowDTO propertyData = propertyService.findByIdWithRooms(id);
        model.addAttribute("propertyData", propertyData);
        return "/property/show";
    }


}
