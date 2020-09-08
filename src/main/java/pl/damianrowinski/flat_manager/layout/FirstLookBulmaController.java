package pl.damianrowinski.flat_manager.layout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstLookBulmaController {
    @RequestMapping("/")
    public String firstLook() {
        return "/layout/firstLook";
    }
}
