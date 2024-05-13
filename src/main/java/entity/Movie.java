package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    @Column(name = "IDMovie", nullable = false, length = 10)
    private String iDMovie;

    @Column(name = "MovieName", length = 50)
    private String movieName;

    @Column(name = "Actor", length = 50)
    private String actor;

    @Column(name = "Author", length = 50)
    private String author;

    @Column(name = "AmoutOfLimit")
    private Integer amoutOfLimit;

    @Column(name = "typeOfMovie", length = 50)
    private String typeOfMovie;

    public String getIDMovie() {
        return iDMovie;
    }

    public void setIDMovie(String iDMovie) {
        this.iDMovie = iDMovie;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getAmoutOfLimit() {
        return amoutOfLimit;
    }

    public void setAmoutOfLimit(Integer amoutOfLimit) {
        this.amoutOfLimit = amoutOfLimit;
    }

    public String getTypeOfMovie() {
        return typeOfMovie;
    }

    public void setTypeOfMovie(String typeOfMovie) {
        this.typeOfMovie = typeOfMovie;
    }

}