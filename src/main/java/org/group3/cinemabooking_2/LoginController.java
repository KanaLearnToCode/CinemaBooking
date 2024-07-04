package org.group3.cinemabooking_2;

import entity.entity.Account;
import jakarta.persistence.NoResultException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.group3.cinemabooking_2.Connection.JDBCUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    private static Account loggedInUser;

    @FXML
    private Text emailError;

    @FXML
    private Text passErr;

    @FXML
    private TextField txEmail;

    @FXML
    private PasswordField txPass;

    @FXML
    void onLogin(ActionEvent event) {
        logInAccount();
    }

    @FXML
    void onRecoverPassWord(MouseEvent event) throws Exception {
        App.setRoot("Account/RecoverAccount");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txEmail.getStyleClass().add("emailLogin");
        txPass.getStyleClass().add("passLogin");

        txEmail.setOnAction(actionEvent -> {
            logInAccount();
        });
        txPass.setOnAction(actionEvent -> {
            logInAccount();
        });
    }

    private void logInAccount() {
        emailError.setText("");
        passErr.setText("");

        String userName = txEmail.getText();
        String password = txPass.getText();


        if (userName.isBlank()) {
            emailError.setText("Invalid email");
        }
        if (password.isBlank()) {
            passErr.setText("Invalid password");
        }
        Connection connection = JDBCUtil.getConnection();
        try {
            String sql = "select * from Account where email = ? ";
            PreparedStatement pS = connection.prepareStatement(sql);
            pS.setString(1, userName);

            ResultSet resultSet = pS.executeQuery();
            Account account = null;
            if (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getInt("IDAccount"));
                account.setEmail(resultSet.getString("Email"));
                account.setPassword(resultSet.getString("Password"));
                account.setPhoneNumber(resultSet.getString("PhoneNumber"));
                account.setName(resultSet.getString("Name"));
                account.setRole(resultSet.getString("Role"));
                account.setAvatar(resultSet.getString("Avatar"));
                account.setDateOfBirth(LocalDate.parse(resultSet.getString("DateOfBirth")));
            }
            loggedInUser = account;

            if (account == null) {
                emailError.setText("Invalid email");
                passErr.setText("Invalid password");
            } else {
                if (!account.getEmail().equals(txEmail.getText())) {
                    emailError.setText("Invalid email");
                }
                if (!account.getPassword().equals(txPass.getText())) {
                    passErr.setText("Invalid password");
                }
                if (account.getEmail().equals(txEmail.getText()) && account.getPassword().equals(txPass.getText())) {
                    App.setRoot("Admin/AdminView");
                }
            }

        } catch (NoResultException e) {
            emailError.setText("Invalid email or password");
            passErr.setText("Invalid email or password");
        } catch (Exception e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }

    public static Account getLoggedInUser() {
        return loggedInUser;
    }
}
