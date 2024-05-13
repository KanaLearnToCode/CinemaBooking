package entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "IDOrders", nullable = false, length = 10)
    private String iDOrders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDClient")
    private Client iDClient;

    @Column(name = "DateTime")
    private Instant dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDAdminOrder")
    private Account iDAdminOrder;

    @Column(name = "Total")
    private Double total;

    public String getIDOrders() {
        return iDOrders;
    }

    public void setIDOrders(String iDOrders) {
        this.iDOrders = iDOrders;
    }

    public Client getIDClient() {
        return iDClient;
    }

    public void setIDClient(Client iDClient) {
        this.iDClient = iDClient;
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