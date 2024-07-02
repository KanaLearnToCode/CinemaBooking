/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.group3.cinemabooking_2.Account.verificationemail;

/**
 * @author Admin
 */
public class Verificationemail {
    public static void sendVerificationEmail(String email,String verificationCode) {
        // Validate email syntax
        if (EmailValidator.isValid(email)) {
            System.out.println("Email address syntax is valid.");

            // Extract domain from email
            String[] parts = email.split("@");
            String domain = parts[1];

            // Check domain existence
            if (DNSChecker.isDomainValid(domain)) {
                System.out.println("Domain exists.");
            } else {
                System.out.println("Domain does not exist.");
            }
        } else {
            System.out.println("Invalid email address syntax.");
        }
        EmailSender.sendVerificationEmail(email, verificationCode);
    }

}