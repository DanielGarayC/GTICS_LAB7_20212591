package com.example.lab7_20212591.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "roomseats")
public class Roomseat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "roomId")
    private Room room;

    @Size(max = 10)
    @NotNull
    @Column(name = "seatNumber", nullable = false, length = 10)
    private String seatNumber;

    @Size(max = 2)
    @NotNull
    @Column(name = "rowNumber", nullable = false, length = 2)
    private String rowNumber;

    @NotNull
    @Column(name = "isAvailable", nullable = false)
    private Boolean isAvailable = false;

}