package entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ShowTimes")
public class ShowTime {
    @Id
    @Column(name = "IDShowTimes", nullable = false, length = 10)
    private String iDShowTimes;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "endtime")
    private LocalTime endtime;

    @Column(name = "\"date\"")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDMovie")
    private Movie iDMovie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTheater")
    private Theater iDTheater;

    public String getIDShowTimes() {
        return iDShowTimes;
    }

    public void setIDShowTimes(String iDShowTimes) {
        this.iDShowTimes = iDShowTimes;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalTime endtime) {
        this.endtime = endtime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Movie getIDMovie() {
        return iDMovie;
    }

    public void setIDMovie(Movie iDMovie) {
        this.iDMovie = iDMovie;
    }

    public Theater getIDTheater() {
        return iDTheater;
    }

    public void setIDTheater(Theater iDTheater) {
        this.iDTheater = iDTheater;
    }

}