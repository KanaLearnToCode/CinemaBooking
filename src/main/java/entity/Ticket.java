package entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Ticket {
    @Id
    @Column(name = "IDTicket", nullable = false, length = 10)
    private String iDTicket;

    @Column(name = "movieName", length = 50)
    private String movieName;

    @Column(name = "dateTimeBook")
    private Instant dateTimeBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDShowTimes")
    private ShowTime iDShowTimes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTheater")
    private Theater iDTheater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDClient")
    private Client iDClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDSeat")
    private Seat iDSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDAccountBook")
    private Account iDAccountBook;

    @Column(name = "Total")
    private Double total;

    public String getIDTicket() {
        return iDTicket;
    }

    public void setIDTicket(String iDTicket) {
        this.iDTicket = iDTicket;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Instant getDateTimeBook() {
        return dateTimeBook;
    }

    public void setDateTimeBook(Instant dateTimeBook) {
        this.dateTimeBook = dateTimeBook;
    }

    public ShowTime getIDShowTimes() {
        return iDShowTimes;
    }

    public void setIDShowTimes(ShowTime iDShowTimes) {
        this.iDShowTimes = iDShowTimes;
    }

    public Theater getIDTheater() {
        return iDTheater;
    }

    public void setIDTheater(Theater iDTheater) {
        this.iDTheater = iDTheater;
    }

    public Client getIDClient() {
        return iDClient;
    }

    public void setIDClient(Client iDClient) {
        this.iDClient = iDClient;
    }

    public Seat getIDSeat() {
        return iDSeat;
    }

    public void setIDSeat(Seat iDSeat) {
        this.iDSeat = iDSeat;
    }

    public Account getIDAccountBook() {
        return iDAccountBook;
    }

    public void setIDAccountBook(Account iDAccountBook) {
        this.iDAccountBook = iDAccountBook;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}