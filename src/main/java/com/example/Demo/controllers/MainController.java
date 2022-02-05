package com.example.Demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "auth/greeting";
    }

    @GetMapping("/mems")
    public String mems() {
        return "mems/mems_main";
    }
}