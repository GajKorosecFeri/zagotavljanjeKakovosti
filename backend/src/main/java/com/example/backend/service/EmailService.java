package com.example.backend.service;

import com.example.backend.model.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String DEFAULT_RECIPIENT = "gajkorosec58@gmail.com";


    @Value("${spring.mail.username}")
    private String emailSender;

    public void sendEmail(EmailDetails emailDetails){
        try {
            log.debug("Preparing to send email to: {}", DEFAULT_RECIPIENT);
            SimpleMailMessage mailMsg = new SimpleMailMessage();
            mailMsg.setFrom(emailSender);
            mailMsg.setTo(DEFAULT_RECIPIENT);
            mailMsg.setText(emailDetails.getMessageBody());
            mailMsg.setSubject(emailDetails.getSubject());

            // Log email details (avoid logging sensitive info like passwords)
            log.debug("Email details: From: {}, To: {}, Subject: {}", emailSender, emailDetails.getRecipient(), emailDetails.getSubject());

            // Send the email
            javaMailSender.send(mailMsg);

            // Log success
            log.info("Mail sent successfully to: {}", emailDetails.getRecipient());
        } catch (MailException exception) {
            // Log the exception with detailed information
            log.error("Failure occurred while sending email to {}: {}", emailDetails.getRecipient(), exception.getMessage(), exception);
            throw new RuntimeException("Email sending failed", exception);
        }
    }
}
