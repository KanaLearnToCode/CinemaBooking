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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ComingSoonController implements Initializable {
    @FXML
    private Label categoryMovie;

    @FXML
    private Label dayTime;

    @FXML
    private Label movieName;

    @FXML
    private ImageView posterMovie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Movie movie) throws FileNotFoundException {
        InputStream inputStreamImage = new FileInputStream(movie.getImagesPoster());
        Image image = new Image(inputStreamImage);
        posterMovie.setImage(image);
        movieName.setText(movie.getMovieName());
        categoryMovie.setText(movie.getTypeOfMovie());
        LocalDate currentTime = LocalDate.now();
        dayTime.setText("" + currentTime);
    }
}
