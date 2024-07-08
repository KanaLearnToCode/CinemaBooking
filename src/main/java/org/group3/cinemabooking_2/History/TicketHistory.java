/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.group3.cinemabooking_2.History;

public class TicketHistory {

    private int idTicket;
    private String clientName;
    private String dateTimeBook;
    private String idSeat;
    private int idTheater;
    private int idSeatShowTime;
    private String movieName;
    private float total;

    // Constructor
    public TicketHistory(int idTicket, String clientName, String dateTimeBook, String idSeat,
                         int idTheater, int idSeatShowTime, String movieName, float total) {
        this.idTicket = idTicket;
        this.clientName = clientName;
        this.dateTimeBook = dateTimeBook;
        this.idSeat = idSeat;
        this.idTheater = idTheater;
        this.idSeatShowTime = idSeatShowTime;
        this.movieName = movieName;
        this.total = total;
    }

    // Getters and Setters
    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
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

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

