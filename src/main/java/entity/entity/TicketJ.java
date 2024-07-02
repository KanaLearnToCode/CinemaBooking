package entity.entity;

import javafx.scene.shape.Circle;

public class TicketJ {
    private Integer id;
    private String DateTimeBook, emailClient, seatID, showTimesID, iDAccountBook;
    private float Total;
    private Circle seatCircle;

    public TicketJ() {
    }

    public Circle getSeatCircle() {
        return seatCircle;
    }

    public void setSeatCircle(Circle seatCircle) {
        this.seatCircle = seatCircle;
    }

    public TicketJ(Integer id, String dateTimeBook, String emailClient, String seatID, String showTimesID, String iDAccountBook, float total, Circle seatCircle) {
        this.id = id;
        DateTimeBook = dateTimeBook;
        this.emailClient = emailClient;
        this.seatID = seatID;
        this.showTimesID = showTimesID;
        this.iDAccountBook = iDAccountBook;
        Total = total;
        this.seatCircle = seatCircle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateTimeBook() {
        return DateTimeBook;
    }

    public void setDateTimeBook(String dateTimeBook) {
        DateTimeBook = dateTimeBook;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    public String getShowTimesID() {
        return showTimesID;
    }

    public void setShowTimesID(String showTimesID) {
        this.showTimesID = showTimesID;
    }

    public String getiDAccountBook() {
        return iDAccountBook;
    }

    public void setiDAccountBook(String iDAccountBook) {
        this.iDAccountBook = iDAccountBook;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }
}
