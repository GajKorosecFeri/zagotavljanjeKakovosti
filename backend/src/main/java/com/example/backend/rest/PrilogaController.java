package com.example.backend.rest;

import com.example.backend.model.Opravilo;
import com.example.backend.model.Priloga;
import com.example.backend.repo.OpraviloRepo;
import com.example.backend.repo.PrilogaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PrilogaController {


    @Autowired
    private OpraviloRepo opraviloRepo;
    @Autowired
    private PrilogaRepo prilogaRepo;

    @PostMapping("/opravila/{id}/priloge")
    public ResponseEntity<String> dodajPrilogo(

            @PathVariable Long id,
            @RequestParam("priloga") MultipartFile file) {
        try {
            // Preverite ali opravilo obstaja
            Opravilo opravilo = opraviloRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Opravilo ne obstaja"));

            // Shranite datoteko na strežnik oz v bazo
            Priloga priloga = new Priloga();
            priloga.setIme(file.getOriginalFilename());
            priloga.setTip(file.getContentType());
            priloga.setPovezava("pot_do_datoteke/" + file.getOriginalFilename()); // Prilagodite shranjevanje
            priloga.setOpravilo(opravilo);

            prilogaRepo.save(priloga);

            return ResponseEntity.ok("Priloga uspešno dodana.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Napaka pri nalaganju priloge: " + e.getMessage());
        }
    }

    @GetMapping("/priloge")
    public ResponseEntity<List<Priloga>> pridobiVsePriloge(){
        List<Priloga> priloge = prilogaRepo.findAll();
        return  ResponseEntity.ok(priloge);
    }

    @GetMapping("/opravila/{opraviloId}/priloge")
    public ResponseEntity<List<Priloga>> pridobiVsePrilogeVOpravilu(@PathVariable Long opraviloId) {
        List<Priloga> priloge = prilogaRepo.findAllByOpraviloId(opraviloId);
        return ResponseEntity.ok(priloge);
    }



}
