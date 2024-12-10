package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "priloge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Priloga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime; // Ime datoteke
    private String tip; // Tip datoteke (npr. pdf, jpg)
    private String povezava; // URL ali pot do datoteke

    @ManyToOne
    @JoinColumn(name = "opravilo_id")
    @JsonBackReference
    private Opravilo opravilo; // Povezava na pripadajoče opravilo
}
