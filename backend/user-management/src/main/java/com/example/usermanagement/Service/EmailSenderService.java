package com.example.usermanagement.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = "<html>" +
                    "<body>" +
                    "<h1>Welcome to Our Service</h1>" +
                    "<p>Dear User,</p>" +
                    "<p>Thank you for joining our service. We are excited to have you on board.</p>" +
                    "<p>" + content + "</p>" +
                    "<p>Best regards,<br/>The Team</p>" +
                    "</body>" +
                    "</html>";

            helper.setFrom("jobgeniouslk@gmail.com", "EAD");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}