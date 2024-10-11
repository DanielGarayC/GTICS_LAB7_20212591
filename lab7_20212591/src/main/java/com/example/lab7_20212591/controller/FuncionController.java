package com.example.lab7_20212591.controller;

import com.example.lab7_20212591.entity.Funciones;
import com.example.lab7_20212591.entity.Reservation;
import com.example.lab7_20212591.repository.FuncionesRepository;
import com.example.lab7_20212591.repository.ObraRepository;
import com.example.lab7_20212591.repository.ReservationRepository;
import com.example.lab7_20212591.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class FuncionController {
    final FuncionesRepository funcionesRepository;
    final ObraRepository obraRepository;
    final RoomRepository roomRepository;
    public FuncionController(FuncionesRepository funcionesRepository, ObraRepository obraRepository, RoomRepository roomRepository){
        this.funcionesRepository = funcionesRepository;
        this.obraRepository = obraRepository;
        this.roomRepository = roomRepository;
    }
    @GetMapping("/Funcion/listaFunciones")
    public String listaFunciones(Model model){
        model.addAttribute("funciones", funcionesRepository.findAll());
        return "Funciones/listaFunciones";
    }
    @GetMapping("/Funcion/agregar")
    public String agregarFuncion (@ModelAttribute("funcion") Funciones funcion, Model model){
        model.addAttribute("obras", obraRepository.findAll());
        model.addAttribute("salas", roomRepository.findAll());

        return "Funciones/formFuncion";
    }
    @GetMapping("/Funcion/editar")
    public String editarFuncion(@RequestParam("id") Integer id, Model model){
        Optional<Funciones> funcion = funcionesRepository.findById(id);
        if (funcion.isPresent()) {
            model.addAttribute("funcion", funcion);
            model.addAttribute("obras", obraRepository.findAll());
            model.addAttribute("salas", roomRepository.findAll());
            return "Funciones/formFuncion";
        }
        return "Funciones/listaFunciones";
    }
    @PostMapping("/Funcion/guardar")
    public String guardarFuncion(@ModelAttribute("funcion") Funciones funcion) {
        funcionesRepository.save(funcion);
        return "redirect:/Funcion/listaFunciones";
    }
    @GetMapping("/Funcion/eliminar")
    public String eliminarFuncion(Funciones funcion){
        funcionesRepository.deleteById(funcion.getId());
        return "redirect:/Funcion/listaFunciones";
    }
}
