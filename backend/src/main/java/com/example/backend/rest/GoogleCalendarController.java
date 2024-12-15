package com.example.backend.rest;

import com.example.backend.scheduler.GoogleEventRequest;
import com.example.backend.service.GoogleCalendarService;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/google-calendar")
public class GoogleCalendarController {

    private final GoogleCalendarService googleCalendarService;

    @Autowired
    public GoogleCalendarController(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    @PostMapping("/add-task")
    public ResponseEntity<?> addEventToGoogleCalendar(@RequestBody GoogleEventRequest request) {
        try {
            // Log incoming request for debugging
            System.out.println("Received Event Request: " + request);

            Event createdEvent = googleCalendarService.createEvent(request);
            return ResponseEntity.ok(createdEvent);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Napaka pri dodajanju dogodka: " + e.getMessage());
        }
    }
}
