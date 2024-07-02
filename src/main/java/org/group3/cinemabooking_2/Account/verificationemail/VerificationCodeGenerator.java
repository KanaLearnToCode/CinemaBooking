/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.group3.cinemabooking_2.Account.verificationemail;

/**
 *
 * @author Admin
 */
import java.util.Random;

public class VerificationCodeGenerator {
    // Method to generate a random verification code
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // Generates a random number between 0 and 9999
        
        // Format the code to have 6 digits (pad with zeros if necessary)
        return String.format("%06d", code);
    }
}
