package com.example.mbot2_projekt_v1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/mBot")
    public String mainpage(Model model) {
        return "index";
    }
}
