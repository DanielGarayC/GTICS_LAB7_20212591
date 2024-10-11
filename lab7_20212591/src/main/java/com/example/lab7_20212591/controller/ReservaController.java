package com.example.lab7_20212591.controller;

import com.example.lab7_20212591.repository.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservaController {
    final ReservationRepository reservationRepository;
    public ReservaController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }
    @GetMapping("/Reserva/allReservas")
    public String listarAllReservas(Model model){
        model.addAttribute("reservas",reservationRepository.findAll());
        return "Reservas/todasReservas";
    }
    @GetMapping("/Reserva/misReservas")
    public String listarMisReservas(Model model){
        int id = 2;
        model.addAttribute("reservas",reservationRepository.obtenerReservasPorCliente(id));
        return "Reservas/misReservas";
    }
}
