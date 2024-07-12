package org.group3.cinemabooking_2.Management;

public class Movie {
    private int idMovie;
    private String movieName;
    private String author;
    private int amountOfLimit;
    private String typeOfMovie;
    private String imagesPoster;
    private String imagesBackdrop;

    // Constructor
    public Movie(int idMovie, String movieName, String author, int amountOfLimit, String typeOfMovie, String imagesPoster, String imagesBackdrop) {
        this.idMovie = idMovie;
        this.movieName = movieName;
        this.author = author;
        this.amountOfLimit = amountOfLimit;
        this.typeOfMovie = typeOfMovie;
        this.imagesPoster = imagesPoster;
        this.imagesBackdrop = imagesBackdrop;
    }

    // Getters and setters
    public int getIdMovie() { return idMovie; }
    public void setIdMovie(int idMovie) { this.idMovie = idMovie; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getAmountOfLimit() { return amountOfLimit; }
    public void setAmountOfLimit(int amountOfLimit) { this.amountOfLimit = amountOfLimit; }

    public String getTypeOfMovie() { return typeOfMovie; }
    public void setTypeOfMovie(String typeOfMovie) { this.typeOfMovie = typeOfMovie; }

    public String getImagesPoster() { return imagesPoster; }
    public void setImagesPoster(String imagesPoster) { this.imagesPoster = imagesPoster; }

    public String getImagesBackdrop() { return imagesBackdrop; }
    public void setImagesBackdrop(String imagesBackdrop) { this.imagesBackdrop = imagesBackdrop; }
}
