package tn.corilus.emailservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.corilus.emailservice.entities.EmailDetails;
import tn.corilus.emailservice.services.EmailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mailing")
public class EmailController {
    private final EmailService emailService;

    @KafkaListener(topics = "email-topic", groupId = "email-consumer-group")
    public ResponseEntity<String>
    sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);

        return ResponseEntity.ok().body(status);
    }

}
