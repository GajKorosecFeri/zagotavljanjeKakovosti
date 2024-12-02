package com.example.backend.service;

import com.example.backend.model.EmailDetails;
import com.example.backend.model.Opravilo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import com.example.backend.repo.OpraviloRepo;


@Component
public class EmailScheduler {

    @Autowired
    private OpraviloRepo opraviloRepo;

    @Autowired
    private EmailService emailService;


    @Scheduled(fixedRate = 300000)
    public void sendEmailNotifications(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourFromNow = now.plusHours(1);


        List<Opravilo> opravila = opraviloRepo.findByReminderMethodAndDatumCasBetween(
                "email", now, oneHourFromNow
        );

        for(Opravilo opravilo : opravila){
        try{
            EmailDetails details = new EmailDetails();

            details.setRecipient(opravilo.getUporabnik().getEmail());
            details.setSubject("Reminder = " + opravilo.getAktivnost());
            details.setMessageBody("Hello,\n\nThis is a reminder for your task: '" + opravilo.getAktivnost()
                    + "' scheduled at " + opravilo.getDatumCas() + ".\n\nBest regards,\nYour ToDoApp App");

            emailService.sendEmail(details);

            System.out.println("Email successfully sent for task: " + opravilo.getId());

            }catch(Exception e){
            System.err.println("Failed to send email for task: " + opravilo.getId());
            e.printStackTrace();
        }

        }

    /*
    @Scheduled(cron = "0 0 8 * * ?") // Runs every day at 8 AM
    public void sendReminderEmails() {
        LocalDateTime today = LocalDateTime.now();

        List<Opravilo> opravila = taskRepository.findBydatumCas(today);




        for (Opravilo opravilo : opravila) {
            try {
                EmailDetails emailDetails = new EmailDetails();
//                emailDetails.setSubject("Reminder: " + opravilo.getTitle());
                emailDetails.setRecipient("gajkorosec13@gmail.com");

                emailDetails.setMessageBody("This is a reminder for your task: " + opravilo.getDescription());


                emailService.sendEmail(emailDetails);


                System.out.println("Reminder email sent for task: " + opravilo.getTitle());
            } catch (Exception e) {

                System.err.println("Failed to send email for task: " + opravilo.getTitle());
                e.printStackTrace();
            }


        }

         */
    }



}