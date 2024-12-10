package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="opravilo")
@Data // Lombok za getterje, setterje, toString itd.
@NoArgsConstructor
@AllArgsConstructor
public class Opravilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aktivnost;
    private String opis;
    private boolean opravljeno;

    private LocalDateTime datumCas;
    private String reminderMethod;



    @ManyToOne
    @JoinColumn(name = "uporabnik_id", nullable = false)
    @JsonBackReference
    private Uporabnik uporabnik; // Povezava do entitete Uporabnik

    @OneToMany(mappedBy = "opravilo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Priloga> priloge = new ArrayList<>(); // Seznam prilog
    
}
