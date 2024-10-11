package com.example.lab7_20212591.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SistemaController {
    @GetMapping("/Teatrologin")
    public String loginTeatro(){
        return "loginForm";
    }
    @GetMapping("/registro")
    public String registroTeatro(){
        return "registroForm";
    }
    @PostMapping("/registrar")
    public String registrarUser(){

        return "redirect:/Teatrologin";
    }
}
