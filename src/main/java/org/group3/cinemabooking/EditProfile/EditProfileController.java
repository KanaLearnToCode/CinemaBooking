package org.group3.cinemabooking.EditProfile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {

    @FXML
    private Circle avatarEdit;

    @FXML
    private HBox bookingTable;

    @FXML
    private VBox editProfileTable;

    @FXML
    private Text passwordErr;

    @FXML
    private TextField txName;

    @FXML
    private TextField txPass;

    @FXML
    void onConfirmEditProfile(ActionEvent event) {

    }

    @FXML
    void onEditAvatar(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}