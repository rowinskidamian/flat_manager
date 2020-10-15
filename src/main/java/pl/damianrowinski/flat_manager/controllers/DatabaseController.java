package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.damianrowinski.flat_manager.services.DatabaseResetService;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/database")
public class DatabaseController {

    private final DatabaseResetService databaseResetService;


    @GetMapping("/randomizeData")
    public String addRandomData() {
        return "/database/add-confirmation";
    }

    @PostMapping("/randomizeData")
    public String confirmedAddRandomData() {
        databaseResetService.generateRandomizedDatabase();
        return "redirect:/";
    }

    @GetMapping("/deleteData")
    public String deleteAll() {
        return "/database/delete-confirmation";
    }

    @PostMapping("/deleteData")
    public String confirmedDeleteAll() {
        databaseResetService.deleteAll();
        return "redirect:/";
    }

}
