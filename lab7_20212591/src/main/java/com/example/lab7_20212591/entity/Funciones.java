package com.example.lab7_20212591.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "funciones")
public class Funciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "roomId")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "obraId")
    private Obra obra;

    @NotNull
    @Column(name = "funcionDate", nullable = false)
    private Date funciondate;

    @Column(name = "availableSeats")
    private Integer availableSeats;

}