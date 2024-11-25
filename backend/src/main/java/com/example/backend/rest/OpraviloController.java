package com.example.backend.rest;

import com.example.backend.repo.OpraviloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.model.Opravilo;

import java.util.List;

@RestController
@RequestMapping("/opravila")
public class OpraviloController {

    @Autowired
    private OpraviloRepo opraviloRepo;

    @GetMapping("/test")
    public String testEndpoint() {
        return "Controller is working!";
    }

    //Create - dodajanje novega uporabnika
    @PostMapping
    public Opravilo ustvariOpravilo(@RequestBody Opravilo opravilo){
        return opraviloRepo.save(opravilo);
    }

    //Read - pridobitev vseh uporabnikov
    @GetMapping
    public List<Opravilo> pridobiVsaOpravila(@RequestParam(required = false) Long uporabnikId){
        if (uporabnikId != null) {
            return opraviloRepo.findByUporabnikId(uporabnikId);
        }
        return opraviloRepo.findAll();
    }

    //Read - pridobitev uporabnika po ID-ju
    @GetMapping("/{id}")
    public ResponseEntity<Opravilo> pridobiOpraviloPoId(@PathVariable Long id){
        Opravilo opravilo = opraviloRepo.findById(id).orElseThrow(() -> new RuntimeException("Opravilo ni bilo najdeno"));
        return ResponseEntity.ok(opravilo);
    }

    //Update - posodobitev uporabnika
    @PutMapping("/{id}")
    public ResponseEntity<Opravilo> posodobiOpravilo(@PathVariable Long id, @RequestBody Opravilo opraviloPodatki){
        Opravilo opravilo = opraviloRepo.findById(id).orElseThrow(()-> new RuntimeException("Opravilo ni bilo najdeno"));

        opravilo.setAktivnost(opraviloPodatki.getAktivnost());
        opravilo.setOpis(opraviloPodatki.getOpis());
        opravilo.setOpravljeno(opraviloPodatki.isOpravljeno());
        opravilo.setDatumCas(opraviloPodatki.getDatumCas());
        opravilo.setReminderMethod(opraviloPodatki.getReminderMethod());

        Opravilo posodobljen = opraviloRepo.save(opravilo);
        return ResponseEntity.ok(posodobljen);
    }

    //Delete - brisanje uorabnika
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> izbrisiOpravilo(@PathVariable Long id) {
        Opravilo opravilo = opraviloRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Opravilo ni bilo najdeno"));

        opraviloRepo.delete(opravilo);
        return ResponseEntity.noContent().build();
    }

    //Opravi- posodobi opravilo na opravljeno
    @PutMapping("/{id}/opravi")
    public ResponseEntity<Opravilo> oznaciKotOpravljeno(@PathVariable Long id){
        Opravilo opravilo = opraviloRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Opravilo ni bilo najdeno"));

        opravilo.setOpravljeno(true);
        Opravilo posodobljeno = opraviloRepo.save(opravilo);

        return ResponseEntity.ok(posodobljeno);
    }

    // Iskanje opravil po aktivnosti
    @GetMapping("/search")
    public List<Opravilo> iskanjeOpravila(@RequestParam("aktivnost") String aktivnost) {
        return opraviloRepo.findByAktivnostContainingIgnoreCase(aktivnost);
    }
}
