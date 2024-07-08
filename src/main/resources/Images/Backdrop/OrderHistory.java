package cinemaboking.doanx;

import java.sql.Date;

public class OrderHistory {

    private int idOrderDetail;
    private String clientName;
    private Date dateTime;
    private int idProduct;
    private int quantity;
    private float price;
    private float total;

    public OrderHistory(int idOrderDetail, String clientName, Date dateTime, int idProduct, int quantity, float price, float total) {
        this.idOrderDetail = idOrderDetail;
        this.clientName = clientName;
        this.dateTime = dateTime;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    // Getters and setters
    public int getIdOrderDetail() {
        return idOrderDetail;
    }

    public void setIdOrderDetail(int idOrderDetail) {
        this.idOrderDetail = idOrderDetail;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
