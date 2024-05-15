package org.group3.cinemabooking.Booking.MovieCard;

import entity.Movie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
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

    }
}
