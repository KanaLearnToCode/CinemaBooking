package entity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    @Column(name = "IDMovie", nullable = false)
    private Integer id;

    @Column(name = "MovieName", length = 50)
    private String movieName;

    @Column(name = "Author", length = 50)
    private String author;

    @Column(name = "AmoutOfLimit")
    private Integer amoutOfLimit;

    @Column(name = "TypeOfMovie", length = 50)
    private String typeOfMovie;

    @Column(name = "ImagesPoster", length = 150)
    private String imagesPoster;

    @Column(name = "ImagesBackdrop", length = 150)
    private String imagesBackdrop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
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

    public String getImagesPoster() {
        return imagesPoster;
    }

    public void setImagesPoster(String imagesPoster) {
        this.imagesPoster = imagesPoster;
    }

    public String getImagesBackdrop() {
        return imagesBackdrop;
    }

    public void setImagesBackdrop(String imagesBackdrop) {
        this.imagesBackdrop = imagesBackdrop;
    }

}