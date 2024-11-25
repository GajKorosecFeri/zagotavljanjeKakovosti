package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "uporabniki")
@Data //get,set itd..
@NoArgsConstructor
@AllArgsConstructor
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ime
    private String email; // E-pošta
    private String phoneNumber; // Telefonska številka

    @OneToMany(mappedBy = "uporabnik", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Opravilo> opravila; // Povezava do opravila
}

