package org.group3.cinemabooking.Account;

import entity.Account;
import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.group3.cinemabooking.Account.verificationemail.VerificationCodeGenerator;
import org.group3.cinemabooking.Account.verificationemail.Verificationemail;
import org.group3.cinemabooking.App;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController implements Initializable {

    @FXML
    private Text acceptTermCheckErr;

    @FXML
    private CheckBox ckAcceptTerm;

    @FXML
    private Text codeConfirmErr;

    @FXML
    private VBox confirmVerificationCode;

    @FXML
    private VBox createAccountTable;

    @FXML
    private ComboBox<Integer> dayInDOB;

    @FXML
    private Text dobErrCreate;

    @FXML
    private Text emailErrorCreate;

    @FXML
    private ComboBox<String> monthInDOB;

    @FXML
    private Text passErrCreate;

    @FXML
    private TextField txCodeConfirm;

    @FXML
    private TextField txEmailSU;

    @FXML
    private PasswordField txPassSU;

    @FXML
    private TextField txUserNameSU;

    @FXML
    private ComboBox<Integer> yearInDOB;

    String verificationCode;

    @FXML
    void onCreateAccount(ActionEvent event) throws Exception {
        if (txCodeConfirm.getText().trim().equals(verificationCode)) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();

                Account account = new Account();
                account.setEmail(txEmailSU.getText());
                account.setPassword(txPassSU.getText());
                account.setRole(false);

                entityManager.persist(account);
                entityTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
            App.setRoot("LogInView", "Log In");
        }
    }

    @FXML
    void onBackToLogin(MouseEvent event) throws Exception {
        App.setRoot("LoginView", "Log In");
    }

    @FXML
    void onContinueCreateAccount(ActionEvent event) throws Exception {

        emailErrorCreate.setText("");
        passErrCreate.setText("");
        dobErrCreate.setText("");
        acceptTermCheckErr.setText("");

        String email = txEmailSU.getText();
        String password = txPassSU.getText();

        if (!checkEmail(email)) {
            emailErrorCreate.setText("Please enter the correct email format");
        } else if (checkEmailExist(email))
            emailErrorCreate.setText("Email is already exist");
        if (!checkPassword(password)) {
            passErrCreate.setText("Password should be from 8 to 16 characters");
        }
        Integer day = dayInDOB.getSelectionModel().getSelectedItem();
        String month = monthInDOB.getSelectionModel().getSelectedItem();
        Integer year = yearInDOB.getSelectionModel().getSelectedItem();
        try {
            if (month.equals("February"))
                if (!checkLeapYear(year) && day > 29) dobErrCreate.setText("Please enter the correct date format");
                else if (checkLeapYear(year) && day > 30) dobErrCreate.setText("Please enter the correct date format");
        } catch (NullPointerException e) {
            dobErrCreate.setText("Please choose all fields");
        }

        if (!ckAcceptTerm.isSelected()) {
            acceptTermCheckErr.setText("Please agree to PLUSCinema's Terms of Service and Privacy Policy");
        }
        if (acceptTermCheckErr.getText().isBlank() && dobErrCreate.getText().isBlank() &&
                passErrCreate.getText().isBlank() && emailErrorCreate.getText().isBlank()) {
            verificationCode = VerificationCodeGenerator.generateVerificationCode();
            Verificationemail.sendVerificationEmail(txEmailSU.getText(), verificationCode);
            createAccountTable.setVisible(false);
            confirmVerificationCode.setVisible(true);
        }
    }

    boolean checkEmailExist(String email) {
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

    boolean checkLeapYear(int year) {
        if (year % 400 == 0) return true;
        return year % 4 == 0 && year % 100 != 0;
    }

    boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{8,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createAccountTable.setVisible(true);
        confirmVerificationCode.setVisible(false);

        txEmailSU.setPromptText("Email");
        txUserNameSU.setPromptText("User Name");
        txPassSU.setPromptText("Password");

        ObservableList<String> listMonth = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        ObservableList<Integer> listYear = FXCollections.observableArrayList();
        ObservableList<Integer> listDay = FXCollections.observableArrayList();
        for (int i = 1; i < 32; i++) {
            listDay.add(i);
        }
        LocalDate localDate = LocalDate.now();
        for (int i = localDate.getYear() - 100; i < localDate.getYear() - 13; i++) {
            listYear.add(i);
        }
        dayInDOB.setItems(listDay);
        monthInDOB.setItems(listMonth);
        yearInDOB.setItems(listYear);
    }
}
