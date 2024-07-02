/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.group3.cinemabooking_2.Account.verificationemail;

/**
 *
 * @author Admin
 */

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private static final String SENDER_EMAIL = "thanhluanrev@gmail.com"; // Your Gmail email address
    private static final String SENDER_PASSWORD = "fuly gkdn otaq twgz"; // Your Gmail password
    private static final String SMTP_HOST = "smtp.gmail.com"; // Gmail SMTP server host
    private static final String SMTP_PORT = "587"; // Gmail SMTP server port

    public static void sendVerificationEmail(String recipientEmail, String verificationCode) {
        // SMTP server configuration
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        // Create session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Email Verification");
            message.setText("Your verification code is: " + verificationCode);

            // Send message
            Transport.send(message);
            System.out.println("Verification email sent to: " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
        }
    }
}