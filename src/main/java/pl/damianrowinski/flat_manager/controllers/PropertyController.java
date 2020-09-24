package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.exceptions.FrobiddenAccessException;
import pl.damianrowinski.flat_manager.model.dtos.PropertyDeleteDTO;
import pl.damianrowinski.flat_manager.model.dtos.PropertyEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.PropertyShowDTO;
import pl.damianrowinski.flat_manager.model.dtos.RoomDeleteDTO;
import pl.damianrowinski.flat_manager.services.PropertyService;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

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

    @PostMapping("/delete")
    public String deleteConfirmed(@ModelAttribute("propertyDeleteData") PropertyDeleteDTO propertyDeleteData) {
        log.info("roomData: " + roomDeleteData.toString());

        roomService.delete(roomDeleteData.getId());
        return "redirect:/room/edit_by_property/" + roomDeleteData.getPropertyId();
    }


    @GetMapping("/edit/{propertyId}")
    public String generateEditForm(@PathVariable Long propertyId, Model model) {
        PropertyEditDTO propertyData = propertyService.findToEditById(propertyId);
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

        String loggedUserName = propertyData.getLoggedUserName();
        if (!loggedUserName.equals(LoggedUsername.get()))
            throw new FrobiddenAccessException("Nie masz dostępu do tych danych.");

        if(!propertyId.equals(propertyData.getId()))
            throw new FrobiddenAccessException("Id obiektu z bazy i edytowanego nie zgadzają się.");

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
