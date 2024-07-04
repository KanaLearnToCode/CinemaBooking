/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemaboking.doanx;

import java.sql.Date;

public class History {
    // Common attributes for both TicketHistory and OrderHistory
    private int idTicket;
    private int idOrderDetail;
    private String clientName;
    private String dateTimeBook; // Use String for unified format
    private Date dateTime; // SQL Date for unified format
    private String idSeat; // For TicketHistory only
    private int idTheater; // For TicketHistory only
    private int idSeatShowTime; // For TicketHistory only
    private int idProduct; // For OrderHistory only
    private int quantity; // For OrderHistory only
    private float price; // For OrderHistory only
    private float total;

    // Constructor for TicketHistory
    public History(int idTicket, String clientName, String dateTimeBook, String idSeat, int idTheater, int idSeatShowTime, String movieName, float total) {
        this.idTicket = idTicket;
        this.clientName = clientName;
        this.dateTimeBook = dateTimeBook;
        this.idSeat = idSeat;
        this.idTheater = idTheater;
        this.idSeatShowTime = idSeatShowTime;
        this.total = total;
        this.dateTime = null; // Not used in TicketHistory
        this.idOrderDetail = 0; // Not used in TicketHistory
        this.idProduct = 0; // Not used in TicketHistory
        this.quantity = 0; // Not used in TicketHistory
        this.price = 0; // Not used in TicketHistory
    }

    // Constructor for OrderHistory
    public History(int idOrderDetail, String clientName, Date dateTime, int idProduct, int quantity, float price, float total) {
        this.idOrderDetail = idOrderDetail;
        this.clientName = clientName;
        this.dateTime = dateTime;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.idTicket = 0; // Not used in OrderHistory
        this.dateTimeBook = null; // Not used in OrderHistory
        this.idSeat = null; // Not used in OrderHistory
        this.idTheater = 0; // Not used in OrderHistory
        this.idSeatShowTime = 0; // Not used in OrderHistory
    }

    // Getters and setters for all attributes
    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

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

    public String getDateTimeBook() {
        return dateTimeBook;
    }

    public void setDateTimeBook(String dateTimeBook) {
        this.dateTimeBook = dateTimeBook;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(String idSeat) {
        this.idSeat = idSeat;
    }

    public int getIdTheater() {
        return idTheater;
    }

    public void setIdTheater(int idTheater) {
        this.idTheater = idTheater;
    }

    public int getIdSeatShowTime() {
        return idSeatShowTime;
    }

    public void setIdSeatShowTime(int idSeatShowTime) {
        this.idSeatShowTime = idSeatShowTime;
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
