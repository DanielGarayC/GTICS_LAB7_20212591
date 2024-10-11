package com.example.lab7_20212591.repository;

import com.example.lab7_20212591.entity.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    @Query(nativeQuery = true, value = "SELECT r.id AS id, " +
            "f.obra_id AS obraId, " +
            "f.roomSeatId AS roomId, " +
            "r.seat_number AS seatNumber, " +
            "r.funcion_id AS funcionId, " +
            "f.funcion_date AS funcionDate " +
            "FROM reservations r " +
            "JOIN funciones f ON r.funcionId = f.id " +
            "JOIN users u ON r.user_id = u.id " +
            "WHERE u.id = :idUser")
    List<Reservation> obtenerReservasPorCliente(@Param("idUser") Integer idUser);
}
