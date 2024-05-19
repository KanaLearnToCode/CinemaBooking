package org.group3.cinemabooking.Booking;

import entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import org.group3.cinemabooking.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingDetailController implements Initializable {

    @FXML
    private Text author;

    @FXML
    private Label checkoutBtn;

    @FXML
    private Text durationMovie;

    @FXML
    private Text movieName;

    @FXML
    private VBox seatManage;

    @FXML
    private Text typeOfMovie;

    @FXML
    private ImageView backgroundMovie;

    @FXML
    void onCheckOut(MouseEvent event) {

    }

    @FXML
    void onbackToBookingTable(MouseEvent event) throws Exception {
        App.setRoot("Admin/AdminView", "AdminView");
    }

    private void createSeats() throws IOException {
        char[] rows = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        int numberOfSeatsPerRow = 10;
        seatManage.setAlignment(Pos.CENTER);
        seatManage.setSpacing(27);
        for (char row : rows) {
            HBox rowContainer = new HBox(23); // HBox for each row with spacing of 23
            rowContainer.setPrefWidth(200);
            rowContainer.setPrefHeight(100);
            rowContainer.setAlignment(Pos.CENTER);
            for (int seatNumber = 1; seatNumber <= numberOfSeatsPerRow; seatNumber++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/group3/cinemabooking/Booking/Seat.fxml"));
                AnchorPane seat = fxmlLoader.load();
                SeatController seatController = fxmlLoader.getController();
                seatController.setID(row + String.valueOf(seatNumber));

                String seatID = row + String.valueOf(seatNumber);
                addSeatClickHandler(seatController.getCircle(), seatID);
                rowContainer.getChildren().add(seat); // Add seat to the row
            }
            seatManage.getChildren().add(rowContainer); // Add row to the container
        }
    }

    private void addSeatClickHandler(Circle seat, String seatNumber) {
        seat.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println(seat.getId());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            createSeats();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStream image;
        try {
            image = new FileInputStream("src/main/resources/Images/Backdrop/backdropKungfuPanda.jpg");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        backgroundMovie.setImage(new Image(image));
        Movie movie = BookingController.getMovie();
        durationMovie.setText(movie.getAmoutOfLimit() + " mins");
        movieName.setWrappingWidth(268);
        movieName.setText(movie.getMovieName());
        author.setText(movie.getAuthor());
        typeOfMovie.setText(movie.getTypeOfMovie());

    }
}
