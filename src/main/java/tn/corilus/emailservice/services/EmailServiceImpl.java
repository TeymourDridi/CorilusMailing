package tn.corilus.emailservice.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.corilus.emailservice.entities.EmailDetails;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private static final Logger logger = Logger.getLogger("Notification");
    @Value("${spring.mail.username}") private String sender;
    @Override
    public String sendSimpleMail(EmailDetails details)
    {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            helper.setFrom(sender);
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            String situation =details.getSubject().contains("Approuvé") ? "Félicitations ! Votre compte a été approuvé.":details.getSubject().contains("Débloqué") ? "Félicitations ! Votre compte a été débloqué." : "Désolé ! Votre compte a été bloqué.";
            helper.setText( "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Corilus - Compte Approuvé</title>\n" +
                    "</head>\n" +
                    "<body style=\"font-family: Arial, sans-serif; background-color: #f2f2f2; margin: 0; padding: 0;\">\n" +
                    "    <div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px;\">\n" +
                    "        <img src=\"https://www.corilus.be/hs-fs/hubfs/Corilus_July2019%20Theme/Images/logo_Corilus_for+site-1.png?width=181&name=logo_Corilus_for+site-1.png\" alt=\"Logo Corilus\" style=\"display: block; margin: 0 auto; max-width: 200px;\">\n" +
                    "        <h1 style=\"color: #2a4d7e; text-align: center; margin-top: 20px;\">"+details.getSubject()+"</h1>\n" +
                    "        <p style=\"color: #333333; text-align: center;\">Cher "+ details.getLastName()+" "+details.getName()+",</p>\n" +
                    "        <p style=\"color: #333333; text-align: center;\">"+  situation+"</p>\n" +
                    "        \n" +
                    "        <table style=\"width: 100%; margin-top: 30px; border-collapse: collapse;\">\n" +
                    "            <tr style=\"background-color: #f2f2f2;\">\n" +
                    "                <td style=\"padding: 10px; text-align: center; font-weight: bold; color: #333333;\">Nom d'utilisateur</td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <td style=\"padding: 10px; text-align: center; color: #333333;\">"+details.getUserName()+"</td>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "        \n" +
                    "        <p style=\"color: #333333; text-align: center; margin-top: 30px;\">Veuillez vous connecter en utilisant les informations d'identification fournies.</p>\n" +
                    "        \n" +
                    "        <p style=\"color: #333333; text-align: center; margin-top: 30px;\">Si vous avez des questions ou avez besoin d'aide supplémentaire, n'hésitez pas à nous contacter directement  +32 (0)81 77 99 30 </p>\n" +
                    "        \n" +
                    "        <p style=\"color: #333333; text-align: center; margin-top: 30px;\">Merci de faire partie de Corilus.</p>\n" +
                    "        \n" +
                    "        <p style=\"color: #333333; text-align: center; margin-top: 30px;\">Cordialement,<br>L'équipe Corilus</p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n",true);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            logger.info("Mail Sent Successfully");
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return "Error while Sending Mail";
        }
    }

}
