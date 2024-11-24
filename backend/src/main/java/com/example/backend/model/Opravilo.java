package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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


    public LocalDateTime getDatumCas() {
        return datumCas;
    }

    public String getDescription() {
        return opis;
    }

    public String getTitle() {
        return aktivnost;
    }
}
