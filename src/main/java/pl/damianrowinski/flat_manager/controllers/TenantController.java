package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damianrowinski.flat_manager.model.dtos.TenantShowDTO;
import pl.damianrowinski.flat_manager.services.TenantService;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import java.util.List;

@Controller
@RequestMapping("/tenant")
@RequiredArgsConstructor

public class TenantController {

    private final TenantService tenantService;

    @RequestMapping
    public String redirectToList() {
        return "redirect:/tenant/list";
    }

    @RequestMapping("/list")
    public String generateList(Model model) {
        List<TenantShowDTO> tenantList = tenantService.findAllLoggedUser(LoggedUsername.get());
        model.addAttribute("tenantList", tenantList);
        return "/tenant/list";
    }


}
