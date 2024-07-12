package org.group3.cinemabooking_2.Management;


import java.sql.Time;
import java.sql.Date;

public class ShowTimes {
    private int idShowTimes;
    private Time startTime;
    private Time endTime;
    private Date date;
    private int idMovie;
    private int idTheater;

    // Constructor
    public ShowTimes(int idShowTimes, Time startTime, Time endTime, Date date, int idMovie, int idTheater) {
        this.idShowTimes = idShowTimes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.idMovie = idMovie;
        this.idTheater = idTheater;
    }

    // Getters and Setters
    public int getIdShowTimes() {
        return idShowTimes;
    }

    public void setIdShowTimes(int idShowTimes) {
        this.idShowTimes = idShowTimes;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public int getIdTheater() {
        return idTheater;
    }

    public void setIdTheater(int idTheater) {
        this.idTheater = idTheater;
    }
}
