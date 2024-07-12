package org.group3.cinemabooking_2.Management;

public class Account {
    private int idAccount;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String dateOfBirth;
    private String role;
    private String avatar;

    // Constructor
    public Account(int idAccount, String name, String email, String password, String phoneNumber, String dateOfBirth, String role, String avatar) {
        this.idAccount = idAccount;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.avatar = avatar;
    }

    public Account() {

    }

    // Getters and setters
    public int getIdAccount() { return idAccount; }
    public void setIdAccount(int idAccount) { this.idAccount = idAccount; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }


}
