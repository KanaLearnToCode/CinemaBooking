package org.group3.cinemabooking.EditProfile;

import entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.group3.cinemabooking.LoginController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileController implements Initializable {

    @FXML
    private Circle avatarEdit;

    @FXML
    private HBox bookingTable;

    @FXML
    private ComboBox<Integer> dayInDOB;

    @FXML
    private VBox editProfileTable;

    @FXML
    private ComboBox<String> monthInDOB;

    @FXML
    private Text newNameErr;

    @FXML
    private Text newPassErr;

    @FXML
    private Text newPhoneNumberErr;

    @FXML
    private TextField txNewName;

    @FXML
    private TextField txNewPass;

    @FXML
    private TextField txNewPhoneNumber;

    @FXML
    private ComboBox<Integer> yearInDOB;

    @FXML
    void onConfirmEditProfile(ActionEvent event) {
        newNameErr.setText("");
        newPassErr.setText("");
        newPhoneNumberErr.setText("");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();

            Account getAccount = LoginController.getLoggedInUser();
            Account account = entityManager.find(Account.class, getAccount.getIDAccount());

            if (!checkPassword(txNewPass.getText())) {
                newPassErr.setText("password must be letter and number, from 8-16 characters");
            }
            if (!checkName(txNewName.getText())) {
                newNameErr.setText("name must be alphabet and from 3-50 characters");
            }
            if (!checkPhoneNumber(txNewPhoneNumber.getText())) {
                newPhoneNumberErr.setText("phone number must be number and 10 characters");
            }
            LocalDate newBirthday = LocalDate.of(yearInDOB.getSelectionModel().getSelectedItem(),
                    Month.valueOf(monthInDOB.getSelectionModel().getSelectedItem().toUpperCase()).getValue(), dayInDOB.getSelectionModel().getSelectedItem());
            if (newPassErr.getText().isBlank() && newNameErr.getText().isBlank() && newPhoneNumberErr.getText().isBlank()) {
                Account updateAccount = new Account();
                updateAccount.setIDAccount(account.getIDAccount());
                updateAccount.setEmail(account.getEmail());
                updateAccount.setName(txNewName.getText());
                updateAccount.setRole(account.getRole());
                updateAccount.setAvatar(account.getAvatar());
                updateAccount.setPhoneNumber(txNewPhoneNumber.getText());
                updateAccount.setPassword(txNewPass.getText());
                updateAccount.setDateOfBirth(newBirthday);

                entityManager.merge(updateAccount);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Update Successfully");
            alert.setTitle("Notification");
            alert.show();

            entityTransaction.commit();
        } catch (Exception e) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @FXML
    void onEditAvatar(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();

            Account getAccount = LoginController.getLoggedInUser();
            Account account = entityManager.find(Account.class, getAccount.getIDAccount());
            LocalDate birthDay = account.getDateOfBirth();
            LocalDate currentTime = LocalDate.now();

            InputStream inputStream = new FileInputStream(getAccount.getAvatar());
            Image image = new Image(inputStream, 50, 50, false, false);
            avatarEdit.setFill(new ImagePattern(image));

            ObservableList<String> listMonth = FXCollections.observableArrayList(
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
            );
            ObservableList<Integer> listYear = FXCollections.observableArrayList();
            for (int i = currentTime.getYear() - 100; i <= currentTime.getYear() - 13; i++) {
                listYear.add(i);
            }

            monthInDOB.setItems(listMonth);
            yearInDOB.setItems(listYear);

            monthInDOB.getSelectionModel().select(birthDay.getMonthValue() - 1);
            yearInDOB.getSelectionModel().select((Integer) birthDay.getYear());

            updateDays();
            dayInDOB.getSelectionModel().select((Integer) birthDay.getDayOfMonth());
            monthInDOB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateDays());
            yearInDOB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateDays());

            txNewName.setText(account.getName());
            txNewPass.setText(account.getPassword());
            txNewPhoneNumber.setText(account.getPhoneNumber());
            entityTransaction.commit();
        } catch (Exception e) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    private boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{8,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean checkName(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z ]{3,50}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private void updateDays() {
        if (monthInDOB.getSelectionModel().getSelectedItem() != null && yearInDOB.getSelectionModel().getSelectedItem() != null) {
            String selectedMonth = monthInDOB.getSelectionModel().getSelectedItem();
            int selectedYear = yearInDOB.getSelectionModel().getSelectedItem();
            int monthIndex = monthInDOB.getItems().indexOf(selectedMonth) + 1;

            YearMonth yearMonth = YearMonth.of(selectedYear, monthIndex);
            int daysInMonth = yearMonth.lengthOfMonth();

            ObservableList<Integer> listDay = FXCollections.observableArrayList();
            for (int i = 1; i <= daysInMonth; i++) {
                listDay.add(i);
            }

            dayInDOB.setItems(listDay);
        }
    }
}
