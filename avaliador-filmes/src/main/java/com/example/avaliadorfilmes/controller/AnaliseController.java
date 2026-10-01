package com.example.avaliadorfilmes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnaliseController {

    @GetMapping("/analises")
    public String listarAnalises() {
        return "analises";
    }
}
