package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Nationalized;

@Entity
public class Account {
    @Id
    @Nationalized
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Nationalized
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "role", nullable = false)
    private Boolean role = false;

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

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

}