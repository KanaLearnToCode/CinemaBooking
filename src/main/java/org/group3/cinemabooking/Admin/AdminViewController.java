package org.group3.cinemabooking.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML
    private Label editProfileLb;

    @FXML
    private HBox hBoxComingSoon;

    @FXML
    private HBox hBoxShowTimes;

    @FXML
    private AnchorPane bookingTable;


    @FXML
    void onEditProfile(MouseEvent event) {

    }

    @FXML
    void seeAllComingSoon(MouseEvent event) {

    }

    @FXML
    void seeAllShowTimes(MouseEvent event) {

    }

    private void loadMovieCards() {
        hBoxShowTimes.getChildren().clear();
        hBoxComingSoon.getChildren().clear();
        try {
            for (int i = 0; i < 5; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MovieCard.fxml"));
                AnchorPane movieCard = fxmlLoader.load();
                hBoxShowTimes.getChildren().add(movieCard);
            }
            for (int i = 0; i < 5; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MovieCard.fxml"));
                AnchorPane movieCard = fxmlLoader.load();
                hBoxComingSoon.getChildren().add(movieCard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMovieCards();
    }
}
