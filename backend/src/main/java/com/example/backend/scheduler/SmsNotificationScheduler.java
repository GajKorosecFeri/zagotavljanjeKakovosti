package com.example.backend.scheduler;

import com.example.backend.model.Opravilo;
import com.example.backend.repo.OpraviloRepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SmsNotificationScheduler {

    @Autowired
    private OpraviloRepo opraviloRepo;

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    // Vsakih 5 minut preverjamo, ali je treba poslati SMS
    @Scheduled(fixedRate = 300000)
    public void sendSmsNotifications() {
        Twilio.init(twilioAccountSid, twilioAuthToken);

        // Poiščemo opravila, ki so na vrsti za obvestila
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourFromNow = now.plusHours(1);

        List<Opravilo> opravila = opraviloRepo.findByReminderMethodAndDatumCasBetween(
                "SMS", now, oneHourFromNow
        );

        for (Opravilo opravilo : opravila) {
            try {
                // Pošljemo SMS
                Message.creator(
                        new com.twilio.type.PhoneNumber(opravilo.getUporabnik().getPhoneNumber()),
                        new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                        "Pozdravljeni, opravilo '" + opravilo.getAktivnost() + "' je predvideno ob "
                                + opravilo.getDatumCas() + "."
                ).create();

                System.out.println("SMS uspešno poslan za opravilo: " + opravilo.getId());
            } catch (Exception e) {
                System.err.println("Napaka pri pošiljanju SMS za opravilo: " + opravilo.getId());
            }
        }
    }
}
