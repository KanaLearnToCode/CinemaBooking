package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    @Column(name = "IDClient", nullable = false, length = 10)
    private String iDClient;

    @Column(name = "phoneNumber", length = 10)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    private String email;

    public String getIDClient() {
        return iDClient;
    }

    public void setIDClient(String iDClient) {
        this.iDClient = iDClient;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}