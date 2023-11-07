package tn.corilus.emailservice.services;

import tn.corilus.emailservice.entities.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

}
