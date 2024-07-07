package entity.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Ticket {
    @Id
    @Column(name = "IDTicket", nullable = false)
    private Integer id;

    @Column(name = "DateTimeBook")
    private Instant dateTimeBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmailClient")
    private Client emailClient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDAccountBook")
    private Account iDAccountBook;

    @Column(name = "Total")
    private Double total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getDateTimeBook() {
        return dateTimeBook;
    }

    public void setDateTimeBook(Instant dateTimeBook) {
        this.dateTimeBook = dateTimeBook;
    }

    public Client getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(Client emailClient) {
        this.emailClient = emailClient;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
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