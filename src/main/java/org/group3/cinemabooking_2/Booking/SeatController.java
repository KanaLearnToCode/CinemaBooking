package org.group3.cinemabooking_2.Booking;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import javafx.scene.shape.Circle;


import java.net.URL;
import java.util.ResourceBundle;

public class SeatController implements Initializable {

    @FXML
    private Circle seat;

    public void setID(String id) {
        seat.setId(id);
    }

    public String getID() {
        return seat.getId();
    }

    public Circle getCircle() {
        return seat;
    }

    @FXML
    void onBooking(MouseEvent event) {
        BookingDetailController bookingDetailController = BookingDetailController.getInstance();
        bookingDetailController.checkSeat(getCircle());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setID(BookingDetailController.getIdSeat());
        if (BookingDetailController.getReservedSeatList().contains(getID())) {
            getCircle().setStyle("-fx-fill: #ed6775");
        }
        seat.setOnMouseClicked(this::onBooking);
    }
}
