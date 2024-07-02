package entity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class SeatId implements java.io.Serializable {
    private static final long serialVersionUID = -7323677449679422779L;
    @Column(name = "IDSeat", nullable = false, length = 10)
    private String iDSeat;

    @Column(name = "IDShowTime", nullable = false)
    private Integer iDShowTime;

    public String getIDSeat() {
        return iDSeat;
    }

    public void setIDSeat(String iDSeat) {
        this.iDSeat = iDSeat;
    }

    public Integer getIDShowTime() {
        return iDShowTime;
    }

    public void setIDShowTime(Integer iDShowTime) {
        this.iDShowTime = iDShowTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatId entity = (SeatId) o;
        return Objects.equals(this.iDShowTime, entity.iDShowTime) &&
                Objects.equals(this.iDSeat, entity.iDSeat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iDShowTime, iDSeat);
    }

}