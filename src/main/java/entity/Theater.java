package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Theater {
    @Id
    @Column(name = "IDTheater", nullable = false, length = 10)
    private String iDTheater;

    @Column(name = "seatQuantity")
    private Integer seatQuantity;

    public String getIDTheater() {
        return iDTheater;
    }

    public void setIDTheater(String iDTheater) {
        this.iDTheater = iDTheater;
    }

    public Integer getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(Integer seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

}