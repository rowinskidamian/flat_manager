package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.damianrowinski.flat_manager.services.DatabaseResetService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DatabaseController {

    private final DatabaseResetService databaseResetService;


    @RequestMapping("/addrandomdata")
    @ResponseBody
    public String addProperty1() {
        databaseResetService.generateRandomizedDatabase();
        return "dodano dane";
    }

}
