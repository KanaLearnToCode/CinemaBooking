package entity.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ShowTimes")
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDShowTimes", nullable = false)
    private Integer id;

    @Column(name = "StartTime")
    private LocalTime startTime;

    @Column(name = "EndTime")
    private LocalTime endTime;

    @Column(name = "\"date\"")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDMovie")
    private Movie iDMovie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTheater")
    private Theater iDTheater;

    @Column(name = "Price")
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}