package org.group3.cinemabooking.Admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.group3.cinemabooking.App;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML
    private Circle avatar;

    @FXML
    private AnchorPane detailTableEvent;

    @FXML
    void onBooking(MouseEvent event) throws Exception {
        String url = "/org/group3/cinemabooking/Booking/Booking.fxml";
        App.setTableEventVBox(url, detailTableEvent, 5, 0);
    }

    @FXML
    void onFood(MouseEvent event) {

    }

    @FXML
    void onEditProfile(MouseEvent event) throws Exception {
        String urlFXML = "/org/group3/cinemabooking/EditProfile/EditProfile.fxml";
        App.setTableEventHBox(urlFXML, detailTableEvent, 0, 0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String urlFXML = "/org/group3/cinemabooking/Booking/Booking.fxml";
        try {
            App.setTableEventVBox(urlFXML, detailTableEvent, 5, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
