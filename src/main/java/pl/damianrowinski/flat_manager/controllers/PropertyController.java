package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damianrowinski.flat_manager.model.dtos.PropertyAddDTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/property")
@Slf4j
public class PropertyController {

    @GetMapping("/add")
    public String generateAddForm(Model model) {
        model.addAttribute("propertyData", new PropertyAddDTO());
        return "/property/form";
    }


}
