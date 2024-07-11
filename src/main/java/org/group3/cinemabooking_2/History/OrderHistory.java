package org.group3.cinemabooking_2.History;

public class OrderHistory {

    private int idOrderDetail;
    private String clientName;
    private String dateTime;
    private int idProduct;
    private int quantity;
    private float price;
    private float total;

    public OrderHistory(int idOrderDetail, String clientName, String dateTime, int idProduct, int quantity, float price, float total) {
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
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
