package entity.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "IDOrders", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmailClient")
    private Client emailClient;

    @Column(name = "DateTime")
    private Instant dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDAdminOrder")
    private Account iDAdminOrder;

    @Column(name = "Total")
    private Double total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(Client emailClient) {
        this.emailClient = emailClient;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Account getIDAdminOrder() {
        return iDAdminOrder;
    }

    public void setIDAdminOrder(Account iDAdminOrder) {
        this.iDAdminOrder = iDAdminOrder;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}