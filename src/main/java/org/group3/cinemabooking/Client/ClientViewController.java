package org.group3.cinemabooking.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClientViewController {

    @FXML
    private VBox Congratulaiton;

    @FXML
    private Text codeVerificationErr;

    @FXML
    private VBox confirmVerificationCode;

    @FXML
    private Text emailErrorRecover;

    @FXML
    private VBox enterEmailRecover;

    @FXML
    private Text passwordCfErr;

    @FXML
    private Text passwordErr;

    @FXML
    private TextField txEmailRecover;

    @FXML
    private PasswordField txNewPass;

    @FXML
    private PasswordField txNewPassCf;

    @FXML
    private TextField txVerificationCode;

    @FXML
    private VBox updatePassTable;

    @FXML
    void onBackToLogin(MouseEvent event) {

    }

    @FXML
    void onBackToLoginButton(ActionEvent event) {

    }

    @FXML
    void onConfirmNewPass(ActionEvent event) {

    }

    @FXML
    void onConfirmRecover(ActionEvent event) {

    }

    @FXML
    void onContinueRecover(ActionEvent event) {

    }

}
