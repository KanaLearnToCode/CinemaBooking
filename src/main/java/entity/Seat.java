package entity;

import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @Column(name = "IDSeat", nullable = false, length = 10)
    private String iDSeat;

    @Column(name = "reserve")
    private Boolean reserve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTheater")
    private Theater iDTheater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDShowTime")
    private ShowTime iDShowTime;

    public String getIDSeat() {
        return iDSeat;
    }

    public void setIDSeat(String iDSeat) {
        this.iDSeat = iDSeat;
    }

    public Boolean getReserve() {
        return reserve;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    public Theater getIDTheater() {
        return iDTheater;
    }

    public void setIDTheater(Theater iDTheater) {
        this.iDTheater = iDTheater;
    }

    public ShowTime getIDShowTime() {
        return iDShowTime;
    }

    public void setIDShowTime(ShowTime iDShowTime) {
        this.iDShowTime = iDShowTime;
    }

}