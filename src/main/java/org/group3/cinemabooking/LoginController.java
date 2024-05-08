package org.group3.cinemabooking;

import entity.Account;
import jakarta.persistence.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
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
        emailError.setText("");
        passErr.setText("");

        String userName = txEmail.getText();
        String password = txPass.getText();

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;

        if (userName.isBlank()) {
            emailError.setText("Invalid email or password");
        }
        if (password.isBlank()) {
            passErr.setText("Invalid email or password");
        }
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
            entityManager = entityManagerFactory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            Account account = (Account) entityManager.createQuery("select a from Account a where a.email = :email and a.password = :password")
                    .setParameter("email", userName).setParameter("password", password)
                    .getSingleResult();
            loggedInUser = account;
            if (account.getRole()) {
                App.setRoot("Admin", "Admin Controller");
            } else {
                App.setRoot("Client", "Client Controller");
            }
            entityTransaction.commit();
        } catch (Exception e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            assert entityTransaction != null;
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @FXML
    void onRecoverPassWord(MouseEvent event) throws Exception {
        App.setRoot("Account/RecoverAccount", "Recover Account");
    }

    @FXML
    void onSignUp(MouseEvent event) throws Exception {
        App.setRoot("Account/SignUp", "Sign Up");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txEmail.getStyleClass().add("emailLogin");
        txPass.getStyleClass().add("passLogin");
    }

    public static Account getLoggedInUser() {
        return loggedInUser;
    }
}
