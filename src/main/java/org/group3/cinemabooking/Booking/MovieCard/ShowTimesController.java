package org.group3.cinemabooking.Booking.MovieCard;

import entity.Movie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowTimesController implements Initializable {

    @FXML
    private Label Rating;

    @FXML
    private Label categoryMovie;

    @FXML
    private Label movieName;

    @FXML
    private ImageView posterMovie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Movie movie) throws FileNotFoundException {
//        movie.setPoster("src/main/resources/Images/marvelPoster.jpg");
        InputStream inputStream = new FileInputStream(movie.getPoster().trim());
        Image image = new Image(inputStream);

        posterMovie.setImage(image);
        String rating = String.valueOf(movie.getRating());
        Rating.setText(rating + "/10");
        movieName.setText(movie.getName());
    }
}
