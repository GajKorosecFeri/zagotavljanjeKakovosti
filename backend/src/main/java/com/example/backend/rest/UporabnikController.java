package com.example.backend.rest;

import com.example.backend.model.Uporabnik;
import com.example.backend.repo.UporabnikRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/uporabniki")
public class UporabnikController {

    @Autowired
    private UporabnikRepo uporabnikRepo;

    // Pridobi vse uporabnike
    @GetMapping
    public ResponseEntity<List<Uporabnik>> getAllUporabniki() {
        return ResponseEntity.ok(uporabnikRepo.findAll());
    }

    // Ustvari novega uporabnika
    @PostMapping
    public ResponseEntity<Uporabnik> createUporabnik(@RequestBody Uporabnik uporabnik) {
        Uporabnik newUporabnik = uporabnikRepo.save(uporabnik);
        return ResponseEntity.ok(newUporabnik);
    }

    // Pridobi uporabnika po ID
    @GetMapping("/{id}")
    public ResponseEntity<Uporabnik> getUporabnikById(@PathVariable Long id) {
        return uporabnikRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Izbriši uporabnika po ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUporabnik(@PathVariable Long id) {
        if (uporabnikRepo.existsById(id)) {
            uporabnikRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
