package com.example.lab7_20212591.controller;

import com.example.lab7_20212591.entity.Funciones;
import com.example.lab7_20212591.entity.Room;
import com.example.lab7_20212591.repository.FuncionesRepository;
import com.example.lab7_20212591.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RoomController {
    final RoomRepository roomRepository;
    public RoomController(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }
    @GetMapping("/Sala/listaSalas")
    public String listaSalas(Model model){
        model.addAttribute("salas", roomRepository.findAll());
        return "Salas/listaSalas";
    }
    @GetMapping("/Sala/agregar")
    public String agregarFuncion (@ModelAttribute("funcion") Funciones funcion, Model model){

        return "Salas/formSalas";
    }
    @GetMapping("/Sala/editar")
    public String editarSala(@RequestParam("id") Integer id, Model model){
        Optional<Room> sala = roomRepository.findById(id);
        if (sala.isPresent()) {
            model.addAttribute("sala", sala);

            return "Salas/formSalas";
        }
        return "Salas/listaSalas";
    }
    @PostMapping("/Sala/guardar")
    public String guardarFuncion(@ModelAttribute("sala") Room sala) {
        roomRepository.save(sala);
        return "redirect:/Funcion/listaFunciones";
    }
    @GetMapping("/Sala/eliminar")
    public String eliminarFuncion(Funciones funcion){
        roomRepository.deleteById(funcion.getId());
        return "redirect:/Sala/listaSalas";
    }
}
