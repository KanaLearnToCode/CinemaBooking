package org.group3.cinemabooking;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(LoginController.getLoggedInUser().getEmail());
    }
}
