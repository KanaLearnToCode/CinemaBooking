package org.group3.cinemabooking.Booking.MovieCard;

import entity.Movie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.group3.cinemabooking.App;
import org.group3.cinemabooking.Booking.BookingController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
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

    private Movie movie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onBookingDetail(MouseEvent event) throws Exception {
        BookingController.setMovie(movie);
        App.setRoot("Booking/BookingDetail", "BookingDetail");
    }

    public void setData(Movie movie) throws FileNotFoundException {
        InputStream inputStreamImage = new FileInputStream(movie.getImagesPoster());
        Image image = new Image(inputStreamImage);
        posterMovie.setImage(image);
        movieName.setText(movie.getMovieName());
        categoryMovie.setText(movie.getTypeOfMovie());
        this.movie = movie;
    }
}
