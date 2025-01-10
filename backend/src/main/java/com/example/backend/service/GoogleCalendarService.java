package com.example.backend.service;

import com.example.backend.scheduler.GoogleEventRequest;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleCalendarService {

    private final Calendar googleCalendar;

    public GoogleCalendarService(Calendar googleCalendar) {
        this.googleCalendar = googleCalendar;
    }

    public Event createEvent(GoogleEventRequest request) throws IOException {
        if (request.getTitle() == null || request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("Manjkajo podatki: title, startTime ali endTime");
        }

        // Log input request
        System.out.println("Creating event: " + request);

        Event event = new Event()
                .setSummary(request.getTitle())
                .setDescription(request.getDescription())
                .setStart(new EventDateTime()
                        .setDateTime(new com.google.api.client.util.DateTime(request.getStartTime()))
                        .setTimeZone("Europe/Ljubljana"))
                .setEnd(new EventDateTime()
                        .setDateTime(new com.google.api.client.util.DateTime(request.getEndTime()))
                        .setTimeZone("Europe/Ljubljana"));

        return googleCalendar.events()
                .insert("filip.knez1234@gmail.com", event)
                .execute();
    }
}

