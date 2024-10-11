package com.example.lab7_20212591.controller;

import com.example.lab7_20212591.entity.Funciones;
import com.example.lab7_20212591.entity.Obra;
import com.example.lab7_20212591.repository.FuncionesRepository;
import com.example.lab7_20212591.repository.ObraRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
    @GetMapping("/Obra/agregar")
    public String agregarObra (@ModelAttribute("obra") Obra obra, Model model){


        return "Obras/formObras";
    }
    @GetMapping("/Obra/editar")
    public String editarObra(@RequestParam("id") Integer id, Model model){
        Optional<Obra> obra = obraRepository.findById(id);
        if (obra.isPresent()) {
            model.addAttribute("funcion", obra);

            return "Obras/formObras";
        }
        return "Obras/listaObras";
    }
    @PostMapping("/Obra/guardar")
    public String guardarObra(@ModelAttribute("obra") Obra obra) {
        obraRepository.save(obra);
        return "redirect:/Obra/listaObras";
    }
    @GetMapping("/Obra/eliminar")
    public String eliminarObra(Obra obra){
        obraRepository.deleteById(obra.getId());
        return "redirect:/Obra/listaObras";
    }
}
