package org.group3.cinemabooking_2.Management;

public class Client {
    private String emailClient;
    private String phoneNumber;
    private String name;

    public Client(String emailClient, String phoneNumber, String name) {
        this.emailClient = emailClient;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
