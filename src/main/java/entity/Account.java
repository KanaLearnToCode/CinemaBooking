package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @Column(name = "IDAccount", nullable = false, length = 10)
    private String iDAccount;

    @Column(name = "Name", length = 50)
    private String name;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "Password", length = 20)
    private String password;

    @Column(name = "PhoneNumber", length = 10)
    private String phoneNumber;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "Role")
    private Boolean role;

    public String getIDAccount() {
        return iDAccount;
    }

    public void setIDAccount(String iDAccount) {
        this.iDAccount = iDAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

}