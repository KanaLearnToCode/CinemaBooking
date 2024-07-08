package org.group3.cinemabooking_2.Admin;


import entity.entity.Account;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import org.group3.cinemabooking_2.App;
import org.group3.cinemabooking_2.EditProfile.EditProfileController;
import org.group3.cinemabooking_2.LoginController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.ResourceBundle;


public class AdminViewController implements Initializable {

    @FXML
    private Circle avatar;

    @FXML
    private AnchorPane detailTableEvent;

    @FXML
    private Text userName;

    @FXML
    private HBox dashboardTableAd;

    @FXML
    private HBox databaseTableAd;

    @FXML
    private HBox historyTableAd;

    @FXML
    void onBooking(MouseEvent event) throws Exception {
        String url = "/org/group3/cinemabooking_2/Booking/Booking.fxml";
        App.setTableEventVBox(url, detailTableEvent, 5, 0);
    }


    @FXML
    void onDashboardAd(MouseEvent event) throws IOException {
        String url = "/org/group3/cinemabooking_2/Dashboard/dashboard.fxml";
        App.setTableEventVBox(url, detailTableEvent, 5, 0);
    }

    @FXML
    void onDataBaseAd(MouseEvent event) {

    }

    @FXML
    void onFood(MouseEvent event) throws IOException {
        String url = "/org/group3/cinemabooking_2/Food/Food.fxml";
        App.setTableEventVBox(url, detailTableEvent, 5, 0);
    }

    @FXML
    void onHistoryAd(MouseEvent event) throws IOException {
        String url = "/org/group3/cinemabooking_2/History/History.fxml";
        App.setTableEventVBox(url, detailTableEvent, 5, 0);
    }

    @FXML
    void onLogOut(MouseEvent event) throws Exception {
        App.setRoot("LoginView");
    }

    @FXML
    void onEditProfile(MouseEvent event) throws Exception {
        String urlFXML = "/org/group3/cinemabooking_2/EditProfile/EditProfile.fxml";
        App.setTableEventHBox(urlFXML, detailTableEvent, 0, 0);
        EditProfileController.setAdminController(this);
    }

    public void updateInformation(String avatarPath, String newName) {
        try (InputStream inputStream = new FileInputStream(avatarPath)) {
            Image image = new Image(inputStream, 50, 50, false, false);
            avatar.setFill(new ImagePattern(image));
            userName.setText(newName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String urlFXML = "/org/group3/cinemabooking_2/Booking/Booking.fxml";
        Account accountLogin = LoginController.getLoggedInUser();

        if (accountLogin.getRole().equals("staff")) {
            dashboardTableAd.setVisible(false);
            historyTableAd.setVisible(false);
            databaseTableAd.setVisible(false);
        }

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(accountLogin.getAvatar());
            App.setTableEventVBox(urlFXML, detailTableEvent, 5, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(inputStream, 50, 50, false, false);
        avatar.setFill(new ImagePattern(image));
        userName.setText(accountLogin.getName());
    }
}
