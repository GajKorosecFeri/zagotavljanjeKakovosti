package com.example.backend.rest;

import com.example.backend.model.EmailDetails;
import com.example.backend.scheduler.GoogleEventRequest;
import com.example.backend.service.GoogleCalendarService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.example.backend.model.Opravilo;
import com.example.backend.model.Priloga;
import com.example.backend.repo.OpraviloRepo;
import com.example.backend.repo.PrilogaRepo;


import com.example.backend.repo.OpraviloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import com.example.backend.service.EmailService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/opravila")
public class OpraviloController {

    @Autowired
    private OpraviloRepo opraviloRepo;
    @Autowired
    private PrilogaRepo prilogaRepo;

    @Autowired
    private EmailService emailService;



    @GetMapping("/test")
    public String testEndpoint() {
        return "Controller is working!";
    }

    //Create - dodajanje novega uporabnika
    @PostMapping
    public Opravilo ustvariOpravilo(@RequestBody Opravilo opravilo){

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("New Opravilo Created");
        emailDetails.setMessageBody("A new Opravilo has been created with ID: " + opravilo.getId() + ".\n\nDetails:\n" +
                "Aktivnost: " + opravilo.getAktivnost() + "\n" +
                "Opis: " + opravilo.getOpis());
        emailService.sendEmail(emailDetails);


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

    @GetMapping("/test-sms")
    public String testSms() {
        // Nastavi SID, Auth Token, in Twilio številko (lahko prebereš iz properties ali ročno vnesi za testiranje)
        Twilio.init("#", "#");

        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("#"), // Tvoja telefonska številka
                    new com.twilio.type.PhoneNumber("#"), // Twilio številka
                    "To je testno SMS sporočilo iz aplikacije!" // Vsebina sporočila
            ).create();

            return "SMS uspešno poslan. SID: " + message.getSid();
        } catch (Exception e) {
            return "Napaka pri pošiljanju SMS: " + e.getMessage();
        }
    }
    //priloge


}
