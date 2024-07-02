package org.group3.cinemabooking_2.Account;


import entity.entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.group3.cinemabooking_2.Account.verificationemail.VerificationCodeGenerator;
import org.group3.cinemabooking_2.Account.verificationemail.Verificationemail;
import org.group3.cinemabooking_2.App;
import org.group3.cinemabooking_2.Connection.JDBCUtil;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class RecoverAccountController implements Initializable {

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
    private TextField txNewPass;

    @FXML
    private TextField txNewPassCf;

    @FXML
    private TextField txVerificationCode;

    @FXML
    private VBox updatePassTable;

    @FXML
    private VBox Congratulaiton;

    String verificationCode;
    Account recoverAccount;

    @FXML
    void onConfirmNewPass(ActionEvent event) {
        passwordCfErr.setText("");
        passwordErr.setText("");
        SignUpController signUpController = new SignUpController();
        if (!signUpController.checkPassword(txNewPass.getText())) {
            passwordErr.setText("Password should be from 8-16 characters");
        }
        if (!txNewPass.getText().equals(txNewPassCf.getText())) {
            passwordCfErr.setText("Confirm Password should be the same with New Pass");
        }
        if (passwordCfErr.getText().isBlank() && passwordErr.getText().isBlank()) {
            Connection connection = null;
            try {
                connection = JDBCUtil.getConnection();
                String updatePass = "update Account set password = ? where email = ?";
                PreparedStatement ps = connection.prepareStatement(updatePass);
                ps.setString(1, txNewPass.getText());
                ps.setString(2, recoverAccount.getEmail());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                JDBCUtil.closeConnection(connection);
            }
            enterEmailRecover.setVisible(false);
            confirmVerificationCode.setVisible(false);
            updatePassTable.setVisible(false);
            Congratulaiton.setVisible(true);
        }
    }


    @FXML
    void onBackToLoginButton(ActionEvent event) throws Exception {
        App.setRoot("LoginView");
    }

    @FXML
    void onBackToLogin(MouseEvent event) throws Exception {
        App.setRoot("LoginView");
    }

    @FXML
    void onConfirmRecover(ActionEvent event) {
        codeVerificationErr.setText("");
        if (!txVerificationCode.getText().trim().equals(verificationCode)) {
            codeVerificationErr.setText("Invalid Verification Code");
        } else {
            enterEmailRecover.setVisible(false);
            confirmVerificationCode.setVisible(false);
            updatePassTable.setVisible(true);
            Congratulaiton.setVisible(false);
        }
    }

    @FXML
    void onContinueRecover(ActionEvent event) {
        emailErrorRecover.setText("");
        String email = txEmailRecover.getText();
        Connection connection = null;
        String emailCheck = "";
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from Account where email = ?";
            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setString(1, email);
            ResultSet rs = pS.executeQuery();
            while (rs.next()) {
                emailCheck = rs.getString("email");
            }
            if (!emailCheck.isBlank()) {
                recoverAccount.setEmail(email);
                verificationCode = VerificationCodeGenerator.generateVerificationCode();
                Verificationemail.sendVerificationEmail(email, verificationCode);
                enterEmailRecover.setVisible(false);
                confirmVerificationCode.setVisible(true);
                updatePassTable.setVisible(false);
                Congratulaiton.setVisible(false);
            } else {
                emailErrorRecover.setText("Invalid Email");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recoverAccount = new Account();
        enterEmailRecover.setVisible(true);
        confirmVerificationCode.setVisible(false);
        updatePassTable.setVisible(false);
        Congratulaiton.setVisible(false);
    }
}
