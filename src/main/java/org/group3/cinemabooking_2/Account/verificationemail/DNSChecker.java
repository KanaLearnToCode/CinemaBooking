/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.group3.cinemabooking_2.Account.verificationemail;

/**
 *
 * @author Admin
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSChecker {
    public static boolean isDomainValid(String domain) {
        try {
            InetAddress.getByName(domain);
            return true; // Domain exists
        } catch (UnknownHostException e) {
            return false; // Domain does not exist
        }
    }
}
