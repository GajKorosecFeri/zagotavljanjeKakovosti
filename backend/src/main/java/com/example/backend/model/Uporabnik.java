package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "uporabniki")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ime
    private String email; // E-pošta
    private String phoneNumber; // Telefonska številka
}

