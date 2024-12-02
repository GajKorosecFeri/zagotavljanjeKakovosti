package com.example.backend.rest;

import com.example.backend.model.EmailDetails;
import com.example.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        try {
            emailService.sendEmail(emailDetails);
            return "Email sent successfully!";
        } catch (RuntimeException e) {
            return "Failed to send email Controller response : " + e.getMessage();
        }
    }
}
