package org.group3.cinemabooking_2.EditProfile;

import entity.entity.Account;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.group3.cinemabooking_2.Admin.AdminViewController;
import org.group3.cinemabooking_2.Connection.JDBCUtil;
import org.group3.cinemabooking_2.LoginController;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.YearMonth;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileController implements Initializable {

    private Stage stage;
    private Account account = LoginController.getLoggedInUser();

    @FXML
    private Circle avatarEdit;

    @FXML
    private ComboBox<Integer> dayInDOB;

    @FXML
    private ComboBox<String> monthInDOB;

    @FXML
    private Text newNameErr;

    @FXML
    private Text newPassErr;

    @FXML
    private Text newCfPassErr;

    @FXML
    private Text newPhoneNumberErr;

    @FXML
    private TextField txNewName;

    @FXML
    private TextField txNewPass;

    @FXML
    private TextField txNewPhoneNumber;

    @FXML
    private PasswordField txNewCfPass;

    @FXML
    private ComboBox<Integer> yearInDOB;

    @FXML
    private HBox editProfileScene;

    public EditProfileController() {

    }

    private static AdminViewController adminViewController;

    public static void setAdminController(AdminViewController admin) {
        adminViewController = admin;
    }

    private void confirmEditprofile() {
        newNameErr.setText("");
        newPassErr.setText("");
        newPhoneNumberErr.setText("");
        newCfPassErr.setText("");
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();

            if (!checkPassword(txNewPass.getText())) {
                newPassErr.setText("Password must be letter and number, from 8-16 characters");
            }
            if (!checkName(txNewName.getText())) {
                newNameErr.setText("Name must be alphabet and from 3-50 characters");
            }
            if (!checkPhoneNumber(txNewPhoneNumber.getText())) {
                newPhoneNumberErr.setText("Phone number must be number and 10 characters");
            }
            if (!txNewCfPass.getText().equals(txNewPass.getText())) {
                newCfPassErr.setText("Confirm Password should be the same with New Pass");
            }

            if (newPassErr.getText().isBlank() && newNameErr.getText().isBlank() && newPhoneNumberErr.getText().isBlank()
                    && newCfPassErr.getText().isBlank()) {
                int year = yearInDOB.getSelectionModel().getSelectedItem();
                int month = monthInDOB.getItems().indexOf(monthInDOB.getSelectionModel().getSelectedItem()) + 1;
                int day = dayInDOB.getSelectionModel().getSelectedItem();


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("");
                alert.setContentText("Confirm Edit Profile ?");
                alert.setTitle("Notification");

                try {
                    alert.getDialogPane().getStylesheets().add(LoginController.class.getResource("CSS/EditProfile/EditProfile.css").toString());
                    alert.getDialogPane().getStyleClass().add("dialog");
                    InputStream inputStream = new FileInputStream("D:\\T1.2308.A0\\7. Java\\3.JP2\\JavaFX\\CinemaBooking_2\\CinemaBooking_2\\src\\main\\resources\\Images\\success.png");
                    Image icon = new Image(inputStream);
                    ImageView imageView = new ImageView(icon);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    alert.setGraphic(imageView);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Platform.runLater(alert::close);
                        String sqlUpdate = "UPDATE Account SET  Name = ?, Avatar =?, PhoneNumber = ?"
                                + ", Password = ?, DateOfBirth = ? WHERE IDAccount = ?";
                        PreparedStatement pS = connection.prepareStatement(sqlUpdate);
                        pS.setString(1, txNewName.getText());
                        pS.setString(2, account.getAvatar());
                        pS.setString(3, txNewPhoneNumber.getText());
                        pS.setString(4, txNewPass.getText());
                        pS.setString(5, year + "/" + month + "/" + day);
                        pS.setInt(6, account.getId());
                        pS.executeUpdate();

                        account.setDateOfBirth(LocalDate.of(year, month, day));
                        account.setPassword(txNewPass.getText());
                        account.setPhoneNumber(txNewPhoneNumber.getText());
                        account.setName(txNewName.getText());
                        adminViewController.updateInformation(account.getAvatar(),account.getName());
                    }
                } catch (Exception e) {
                    System.err.println("Could not load CSS file: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            assert connection != null;
            JDBCUtil.closeConnection(connection);
        }
    }

    @FXML
    void onConfirmEditProfile(ActionEvent event) {
        confirmEditprofile();
    }

    @FXML
    void onEditAvatar(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.setInitialDirectory(new File("D:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG File", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG File", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG File", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.jpg", "*.png", "*.jpeg"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                InputStream inputStream = new FileInputStream(file.getPath());
                Image image = new Image(inputStream);
                avatarEdit.setFill(new ImagePattern(image));
                account.setAvatar(file.getPath());
            } catch (FileNotFoundException e) {
                System.out.println("Not find file");
            }
        } else {
            System.out.println("cant choose file");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editProfileScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirmEditprofile();
            }
        });

        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();

            LocalDate birthDay = account.getDateOfBirth();
            LocalDate currentTime = LocalDate.now();

            InputStream inputStream = new FileInputStream(account.getAvatar());
            Image image = new Image(inputStream);
            avatarEdit.setFill(new ImagePattern(image));

            ObservableList<String> listMonth = FXCollections.observableArrayList(
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
            );
            ObservableList<Integer> listYear = FXCollections.observableArrayList();
            for (int i = currentTime.getYear() - 50; i <= currentTime.getYear() - 18; i++) {
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
            txNewCfPass.setText(account.getPassword());

        } catch (Exception e) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            assert connection != null;
            JDBCUtil.closeConnection(connection);
        }
    }

    private boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{8,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkName(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z ]{3,50}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
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