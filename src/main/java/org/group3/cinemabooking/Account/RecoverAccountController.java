package org.group3.cinemabooking.Account;

import entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.group3.cinemabooking.Account.verificationemail.VerificationCodeGenerator;
import org.group3.cinemabooking.Account.verificationemail.Verificationemail;
import org.group3.cinemabooking.App;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


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
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();

                Account account = new Account();
                account.setEmail(txEmailRecover.getText());
                account.setPassword(txNewPass.getText());

                entityManager.merge(account);
                entityTransaction.commit();
            } catch (Exception e) {
                Logger.getLogger(RecoverAccountController.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                if (entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
            enterEmailRecover.setVisible(false);
            confirmVerificationCode.setVisible(false);
            updatePassTable.setVisible(false);
            Congratulaiton.setVisible(true);
        }
    }


    @FXML
    void onBackToLoginButton(ActionEvent event) throws Exception {
        App.setRoot("LoginView", "Log In");
    }

    @FXML
    void onBackToLogin(MouseEvent event) throws Exception {
        App.setRoot("LoginView", "Log In");
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
        SignUpController signUpController = new SignUpController();
        emailErrorRecover.setText("");
        String email = txEmailRecover.getText();

        if (!signUpController.checkEmail(email)) {
            emailErrorRecover.setText("Please enter the correct email format");
        } else if (!signUpController.checkEmailExist(email))
            emailErrorRecover.setText("This email is not registered");
        if (emailErrorRecover.getText().isBlank()) {
            verificationCode = VerificationCodeGenerator.generateVerificationCode();
            Verificationemail.sendVerificationEmail(txEmailRecover.getText(), verificationCode);
            enterEmailRecover.setVisible(false);
            confirmVerificationCode.setVisible(true);
            updatePassTable.setVisible(false);
            Congratulaiton.setVisible(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterEmailRecover.setVisible(true);
        confirmVerificationCode.setVisible(false);
        updatePassTable.setVisible(false);
        Congratulaiton.setVisible(false);
    }
}
