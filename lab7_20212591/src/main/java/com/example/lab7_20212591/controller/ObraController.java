package com.example.lab7_20212591.controller;

import com.example.lab7_20212591.entity.Obra;
import com.example.lab7_20212591.repository.FuncionesRepository;
import com.example.lab7_20212591.repository.ObraRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ObraController {
    final ObraRepository obraRepository;
    public ObraController(ObraRepository obraRepository){
        this.obraRepository = obraRepository;
    }
    @GetMapping("/Obra/listaObras")
    public String listaObras(Model model){
        model.addAttribute("obras", obraRepository.findAll());
        return "Obras/listaObras";
    }
}
