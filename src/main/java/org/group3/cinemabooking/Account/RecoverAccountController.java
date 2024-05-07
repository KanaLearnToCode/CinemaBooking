package org.group3.cinemabooking.Account;

import entity.Account;
import jakarta.persistence.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecoverAccountController implements Initializable {

    @FXML
    private VBox confirmVerificationCode;

    @FXML
    private Text emailErrorRecover;

    @FXML
    private VBox enterEmailRecover;

    @FXML
    private TextField txEmailRecover;

    @FXML
    private TextField txVerificationCode;

    String verificationCode;

    @FXML
    void onBackToLogin(MouseEvent event) throws Exception {
        App.setRoot("LoginView", "Log In");
    }

    @FXML
    void onConfirmRecover(ActionEvent event) {
        if (txVerificationCode.getText().trim().equals(verificationCode)) {
    
        }
    }

    @FXML
    void onContinueRecover(ActionEvent event) {
        emailErrorRecover.setText("");
        String email = txEmailRecover.getText();

        if (!checkEmail(email)) {
            emailErrorRecover.setText("Please enter the correct email format");
        } else if (checkEmailExist(email))
            emailErrorRecover.setText("Email is already exist");
        if (emailErrorRecover.getText().isBlank()) {
            verificationCode = VerificationCodeGenerator.generateVerificationCode();
            Verificationemail.sendVerificationEmail(txEmailRecover.getText(), verificationCode);
            enterEmailRecover.setVisible(false);
            confirmVerificationCode.setVisible(true);
        }
    }

    private boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean checkEmailExist(String email) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        boolean check = true;

        try {
            entityTransaction.begin();
            Account account = (Account) entityManager.createQuery("select a from Account a where a.email = :email")
                    .setParameter("email", email.toLowerCase()).getSingleResult();
            if (account == null) check = false;
            entityTransaction.commit();
        } catch (NoResultException e) {
            return false;
        } finally {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            entityManager.close();
            entityManagerFactory.close();
        }

        return check;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterEmailRecover.setVisible(true);
        confirmVerificationCode.setVisible(false);
    }
}
