package pl.damianrowinski.flat_manager.module2_analytics.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestModule2 {

    @RequestMapping("/testmodule")
    @ResponseBody
    public String testModule() {
        return "test Module";
    }
}
