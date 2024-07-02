package entity.entity;

import jakarta.persistence.*;

@Entity
public class Seat {
    @EmbeddedId
    private SeatId id;

    @MapsId("iDShowTime")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDShowTime", nullable = false)
    private ShowTime iDShowTime;

    public SeatId getId() {
        return id;
    }

    public void setId(SeatId id) {
        this.id = id;
    }

    public ShowTime getIDShowTime() {
        return iDShowTime;
    }

    public void setIDShowTime(ShowTime iDShowTime) {
        this.iDShowTime = iDShowTime;
    }

}